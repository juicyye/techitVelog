package techit.velog.domain.blog.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techit.velog.domain.blog.entity.Blog;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog,Long>, BlogCustomRepository {
    @Query("select b from Blog b join b.user u where u.loginId = :loginId")
    @EntityGraph(attributePaths = {"user"})
    Optional<Blog> findByLoginId(@Param("loginId") String loginId);

    Optional<Blog> findByTitle(String blogName);

    Optional<Blog> findByUser_Id(Long userId);
}
