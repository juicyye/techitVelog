package techit.velog.domain.comment.repository;

import techit.velog.domain.comment.entity.Comment;

import java.util.List;


public interface CommentCustomRepository {
    List<Comment> findAllByPost(Long postId);
}
