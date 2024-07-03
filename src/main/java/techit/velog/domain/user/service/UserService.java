package techit.velog.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.post.repository.PostsRepository;
import techit.velog.domain.uploadfile.FileStore;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.dto.UserRespDtoWeb;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.exception.CustomWebException;

import java.io.IOException;
import java.util.Optional;

import static techit.velog.domain.user.dto.UserReqDto.*;
import static techit.velog.domain.user.dto.UserRespDtoWeb.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PasswordEncoder  passwordEncoder;
    private final PostsRepository postRepository;
    private final FileStore fileStore;

    @Transactional
    public Long join(UserJoinReq userJoinReq) {
        userJoinReq.setPassword(passwordEncoder.encode(userJoinReq.getPassword()));
        User savedUser = userRepository.save(User.toEntity(userJoinReq));
        blogRepository.save(new Blog("@" + savedUser.getName(), savedUser));
        return savedUser.getId();
    }


    public boolean validationEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean validationLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public UserRespWebInfo getUser(String loginId) {
        Optional<User> _user = userRepository.findByLoginId(loginId);
        if (_user.isEmpty()) {
            throw new CustomWebException("user not found by loginId: " + loginId);
        }
        return new UserRespWebInfo(_user.get());
    }

    public UserRespDtoWebUpdate getUserByUpdate(String loginId) {
        Optional<User> _user = userRepository.findByLoginId(loginId);
        if (_user.isEmpty()) {
            throw new CustomWebException("user not found by loginId: " + loginId);
        }
        UserRespDtoWebUpdate userRespDtoWebUpdate = new UserRespDtoWebUpdate(_user.get());
        Blog blog = blogRepository.findByUser_Id(userRespDtoWebUpdate.getUserId()).orElseThrow(() -> new CustomWebException("not found blog "));
        userRespDtoWebUpdate.setBlogDescription(blog.getDescription());
        return userRespDtoWebUpdate;
    }

    public boolean checkPassword(AccountDto accountDto, String password) {
        if (passwordEncoder.matches(password, accountDto.getPassword())) {
            return true;
        } else{
            return false;
        }
    }
    @Transactional
    public void updateInfo(UserReqDtoWebUpdate userReqDtoWeb, AccountDto accountDto) {
        User user = userRepository.findByLoginId(accountDto.getLoginId()).orElseThrow(() -> new CustomWebException("user not found by loginId: " + accountDto.getLoginId()));
        userReqDtoWeb.setChangePassword(passwordEncoder.encode(userReqDtoWeb.getChangePassword()));
        UploadFile uploadFile = uploadFile(userReqDtoWeb.getUserImage());
        user.changeInfo(userReqDtoWeb,uploadFile);
        Blog blog = blogRepository.findByUser_Id(user.getId()).orElseThrow(() -> new CustomWebException("not found blog"));
        blog.changeDescription(userReqDtoWeb.getBlogDescription());
    }

    @Transactional
    public void deleteUser(AccountDto accountDto) {
        // todo 연관관계 테이블들 삭제`
        User user = userRepository.findByLoginId(accountDto.getLoginId()).orElseThrow(() -> new CustomWebException("user not found by loginId: " + accountDto.getLoginId()));
        Blog blog = blogRepository.findByLoginId(accountDto.getLoginId()).orElseThrow(() -> new CustomWebException("user not found by loginId: " + accountDto.getLoginId()));
        postRepository.deleteByBlog_id(blog.getId());
        userRepository.delete(user);
    }

    /**
     * 싱글 이미지파일 저장하고 객체로 바꾸는 메소드
     */

    private UploadFile uploadFile(MultipartFile file) {
        if (file != null) {
            try {
                return fileStore.storeFile(file);
            } catch (IOException e) {
                log.error("file error {}", e.getMessage());
                throw new CustomWebException(e.getMessage());
            }
        } else {
            return null;
        }
    }
}
