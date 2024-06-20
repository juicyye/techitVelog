package techit.velog.domain.comment.repository;

import techit.velog.domain.comment.dto.CommentRespDto;
import techit.velog.domain.comment.entity.Comment;

import java.util.List;

import static techit.velog.domain.comment.dto.CommentRespDto.*;

public interface CommentCustomRepository {
    List<Comment> findAllByPost(Long postId);
}
