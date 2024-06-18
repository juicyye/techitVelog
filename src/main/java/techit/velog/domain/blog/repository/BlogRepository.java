package techit.velog.domain.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techit.velog.domain.blog.entity.Blog;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    @Query("select b from Blog b join b.user u where u.loginId = :loginId")
    Optional<Blog> findByUserId(@Param("loginId") String loginId);

}
