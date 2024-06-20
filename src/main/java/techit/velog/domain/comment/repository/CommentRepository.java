package techit.velog.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techit.velog.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
}
