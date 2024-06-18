package techit.velog.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techit.velog.domain.post.entity.Posts;

public interface PostRepository extends JpaRepository<Posts, Long> {
}
