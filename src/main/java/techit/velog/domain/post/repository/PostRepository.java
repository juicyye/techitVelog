package techit.velog.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import techit.velog.domain.post.entity.Posts;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Posts, Long>,PostCustomRepository {
    Optional<Posts> findByBlog_Id(Long id);

    void deleteByBlog_id(Long id);

}
