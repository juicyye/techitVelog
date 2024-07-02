package techit.velog.domain.post.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techit.velog.domain.post.entity.Posts;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsCustomRepository {
    List<Posts> findByBlog_Id(Long id);

    void deleteByBlog_id(Long id);

    @Query("select p from Posts p join fetch p.blog b where b.title = :blogName and p.title = :postTitle")
    @EntityGraph(attributePaths = {"blog"})
    Optional<Posts> findPostBlogName(@Param("blogName") String blogName,@Param("postTitle") String postTitle);
}
