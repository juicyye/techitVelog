package techit.velog.global.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import techit.velog.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Slf4j
public class JoinUniqueValidator implements ConstraintValidator<Unique, String> {
    private final UserRepository userRepository;
    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (checkIsEmpty(s)) {
            return false;
        }
        log.info("duplicate {}",allDuplicate(s));
        return allDuplicate(s);
    }

    private static boolean checkIsEmpty(String value) {
        return value == null || value.length() == 0;
    }

    private boolean allDuplicate(String value) {
        return duplicationEmail(value) && duplicationName(value) && duplicationLoginId(value) && duplicationNickname(value);
    }

    private boolean duplicationEmail(String email) {
        log.info("value = {}", email);
        log.info("duplicate email = {}", !userRepository.existsByEmail(email));
        return !userRepository.existsByEmail(email);
    }
    private boolean duplicationName(String name) {
        log.info("value = {}", name);
        log.info("duplicate name = {}", !userRepository.existsByName(name));
        return !userRepository.existsByName(name);
    }
    private boolean duplicationLoginId(String loginId) {
        log.info("value = {}", loginId);
        log.info("duplicate loginId = {}", !userRepository.existsByLoginId(loginId));
        return !userRepository.existsByLoginId(loginId);
    }
    private boolean duplicationNickname(String nickName) {
        log.info("value = {}", nickName);
        log.info("duplicate nickName = {}", !userRepository.existsByNickname(nickName));
        return !userRepository.existsByNickname(nickName);
    }
}
