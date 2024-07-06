package techit.velog.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.comment.repository.CommentRepository;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.repository.PostsRepository;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.exception.CustomWebException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static techit.velog.domain.comment.dto.CommentReqDtoWeb.*;
import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostsRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(CommentReqDtoWebBasic commentReqDtoWebBasic, String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomWebException("유저를 찾을 수 없습니다."));
        Posts posts = postRepository.findById(commentReqDtoWebBasic.getPostId()).orElseThrow(() -> new CustomWebException("포스트를 찾을 수 없습니다."));
        Comment comment = new Comment(commentReqDtoWebBasic, posts, user);
        if (commentReqDtoWebBasic.getCommentId() != null) {
            Comment parent = commentRepository.findById(commentReqDtoWebBasic.getCommentId()).orElseThrow(() -> new CustomWebException("댓글을 찾을 수 없습니다."));
            parent.addChild(comment);
        }
        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    public List<CommentRespDtoWeb> getComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPost(postId);
        return nestedCommentDto(comments);
    }


    public static List<CommentRespDtoWeb> nestedCommentDto(List<Comment> comments) {
        List<CommentRespDtoWeb> commentRespDtoWebs = new ArrayList<>();
        Map<Long, CommentRespDtoWeb> map = new HashMap<>();
        comments.forEach(comment -> {
            CommentRespDtoWeb dto = CommentRespDtoWeb.toDto(comment);
            map.put(dto.getCommentId(), dto);
            if (comment.getParent() != null) {
                CommentRespDtoWeb parent = map.get(comment.getParent().getId());
                if (parent != null) {
                    parent.getChild().add(dto);
                }
            } else{
                commentRespDtoWebs.add(dto);
            }

        });
        return commentRespDtoWebs;

    }


    public CommentReqDtoWebUpdate getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomWebException("not found Comment"));
        return new CommentReqDtoWebUpdate(comment);

    }
    @Transactional
    public boolean update(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomWebException("not found Comment"));
        comment.changeContent(content);
        return true;
    }
    @Transactional
    public boolean deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
        return true;
    }
}
