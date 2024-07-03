package techit.velog.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techit.velog.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
    @Query("select u from User u where u.blog.id = :id")
    Optional<User> findByBlog_Id(@Param("id") Long id);

    @Query("select u from User u join fetch u.blog b where b.title = :title")
    Optional<User> findByBlog_Name(@Param("title") String blogName);
}
