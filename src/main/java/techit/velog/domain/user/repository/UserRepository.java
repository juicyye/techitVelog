package techit.velog.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techit.velog.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
}
