package techit.velog.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;

import static techit.velog.domain.user.dto.UserReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder  passwordEncoder;
    @Transactional
    public Long join(UserJoinReq userJoinReq) {
        userJoinReq.setPassword(passwordEncoder.encode(userJoinReq.getPassword()));
        User savedUser = userRepository.save(User.toEntity(userJoinReq));
        return savedUser.getId();
    }


    public boolean validationEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean validationLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

}
