package techit.velog.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.dto.UserRespDto;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.exception.CustomWebException;

import java.util.Optional;
import java.util.UUID;

import static techit.velog.domain.user.dto.UserReqDto.*;
import static techit.velog.domain.user.dto.UserRespDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PasswordEncoder  passwordEncoder;

    @Transactional
    public Long join(UserJoinReq userJoinReq) {
        userJoinReq.setPassword(passwordEncoder.encode(userJoinReq.getPassword()));
        userJoinReq.setUserId(UUID.randomUUID().toString());
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

    public UserRespDtoWeb getUser(String loginId) {
        Optional<User> _user = userRepository.findByLoginId(loginId);
        if (_user.isEmpty()) {
            throw new CustomWebException("user not found by loginId: " + loginId);
        }
        return new UserRespDtoWeb(_user.get());
    }
}
