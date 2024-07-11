package techit.velog.global.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.dto.PrincipalDetails;
import techit.velog.global.exception.CustomWebException;

@RequiredArgsConstructor
@Slf4j
public class UserUpdateValidator implements ConstraintValidator<UserUpdate, String> {
    private final UserRepository userRepository;
    @Override
    public void initialize(UserUpdate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (checkIsEmpty(s)) {
            return false;
        }
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByLoginId(principalDetails.getUsername()).orElseThrow(() -> new CustomWebException("not found user"));
        return allCheck(s, user);
    }

    private boolean checkIsEmpty(String value) {
        return value == null || value.length() == 0;
    }

    private boolean allCheck(String value, User user) {
        return checkEmail(value, user) && checkName(value,user) && checkNickname(value,user);
    }

    private boolean checkEmail(String email, User user) {
        return user.isUserEmail(email) || !userRepository.existsByEmail(email);
    }

    private boolean checkNickname(String nickname, User user) {
        return user.isUserNickname(nickname) || !userRepository.existsByNickname(nickname);
    }

    private boolean checkName(String name, User user) {
        return user.isUserName(name) || !userRepository.existsByName(name);
    }

}
