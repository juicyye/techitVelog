package techit.velog.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.post.repository.PostsRepository;
import techit.velog.domain.uploadfile.FileStore;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.exception.CustomWebException;

import java.io.IOException;
import java.util.List;
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

    public UserRespDtoWebAdmin getUser(Long userId) {
        Optional<User> _user = userRepository.findById(userId);
        if (_user.isEmpty()) {
            throw new CustomWebException("user not found by loginId: " + userId);
        }
        return UserRespDtoWebAdmin.toDto(_user.get());
    }

    public UserRespDtoWebInfo getUser(String loginId) {
        Optional<User> _user = userRepository.findByLoginId(loginId);
        if (_user.isEmpty()) {
            throw new CustomWebException("user not found by loginId: " + loginId);
        }
        User user = _user.get();
        Blog blog = blogRepository.findByUser_Id(user.getId()).orElseThrow(() -> new CustomWebException("not found blog"));
        return new UserRespDtoWebInfo(user,blog.getTitle());
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

    public boolean checkPassword(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomWebException("not found user"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        } else{
            return false;
        }
    }
    @Transactional
    public void updateInfo(UserReqDtoWebUpdate userReqDtoWeb, String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomWebException("user not found by loginId: " + loginId));
        userReqDtoWeb.setChangePassword(passwordEncoder.encode(userReqDtoWeb.getChangePassword()));
        UploadFile uploadFile = uploadFile(userReqDtoWeb.getUserImage());
        user.changeInfo(userReqDtoWeb,uploadFile);
        Blog blog = blogRepository.findByUser_Id(user.getId()).orElseThrow(() -> new CustomWebException("not found blog"));
        blog.changeInfo(userReqDtoWeb.getBlogDescription(),"@"+user.getName());
    }

    @Transactional
    public void deleteUser(String loginId) {
        // todo 연관관계 테이블들 삭제`
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomWebException("user not found by loginId: " + loginId));
        Blog blog = blogRepository.findByLoginId(loginId).orElseThrow(() -> new CustomWebException("user not found by loginId: " + loginId));
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

    public boolean isUser(SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
           return false;
        }else{
            return true;
        }
    }

    public List<UserRespDtoWebAdmin> getUsers() {
        List<User> users = userRepository.findAll();
        return UserRespDtoWebAdmin.toDto(users);
    }
}
