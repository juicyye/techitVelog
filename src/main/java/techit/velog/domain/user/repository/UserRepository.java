package techit.velog.domain.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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

    Optional<User> findByEmail(String email);

    boolean existsByName(String name);

    boolean existsByNickname(String s);

    @Query(value = "select u from User u left join fetch u.uploadFile uf ", countQuery = "select count(u) from User u")
    Page<User> findAllByAdmin(Pageable pageable);

    @Query("select u from User u join fetch u.blog b join fetch b.posts p where p.id = :postId")
    Optional<User> findByPostId(@Param("postId") Long postId);
    @Query("select u from User u join fetch u.comments c where c.id = :commentId")
    Optional<User> findByCommentId(@Param("commentId") Long commentId);

    @Query("select u from User u left join fetch u.uploadFile where u.loginId = :loginId")
    Optional<User> findByLoginIdImage(@Param("loginId") String loginId);
}
