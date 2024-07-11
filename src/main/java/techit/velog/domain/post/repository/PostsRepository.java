package techit.velog.domain.post.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techit.velog.domain.post.entity.Posts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsCustomRepository {
    List<Posts> findByBlog_Id(Long id);

    void deleteByBlog_id(Long id);

    @Query("select p from Posts p join fetch p.blog b where b.title = :blogName and p.title = :postTitle")
    Optional<Posts> findPostBlogName(@Param("blogName") String blogName,@Param("postTitle") String postTitle);

    @Query("select p from Posts p join fetch p.blog b where b.title = :blogName and p.createDate < :createDate order by p.createDate desc")
    List<Posts> findByPreviousPost(@Param("blogName") String blogName,@Param("createDate") LocalDateTime createDate);

    @Query("select p from Posts p join fetch p.blog b where b.title = :blogName and p.createDate > :createDate order by p.createDate asc")
    List<Posts> findByNextPost(@Param("blogName") String blogName,@Param("createDate") LocalDateTime createDate);

    @EntityGraph(attributePaths = {"uploadFile","uploadFiles"})
    @Query("select p from Posts p where p.id = :id")
    Optional<Posts> findPostByImage(@Param("id") Long postId);
}
