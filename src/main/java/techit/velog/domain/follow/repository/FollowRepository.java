package techit.velog.domain.follow.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.follow.entity.Follow;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowCustomRepository {
    @Query("select f from Follow f where f.from_blog = :from_blog and f.to_blog = :to_blog")
    Optional<Follow> findByFollow(@Param("from_blog") Blog from_blog, @Param("to_blog") Blog to_blog);

}
