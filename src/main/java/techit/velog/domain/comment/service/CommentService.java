package techit.velog.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.comment.dto.webreq.CommentReqDtoWebCreate;
import techit.velog.domain.comment.dto.webreq.CommentReqDtoWebUpdate;
import techit.velog.domain.comment.dto.webresp.CommentRespDtoWeb;
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


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostsRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(CommentReqDtoWebCreate commentReqDtoWebCreate, String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomWebException("유저를 찾을 수 없습니다."));
        Posts posts = postRepository.findById(commentReqDtoWebCreate.getPostId()).orElseThrow(() -> new CustomWebException("포스트를 찾을 수 없습니다."));
        Comment comment = new Comment(commentReqDtoWebCreate, posts, user);
        if (commentReqDtoWebCreate.getCommentId() != null) {
            Comment parent = commentRepository.findById(commentReqDtoWebCreate.getCommentId()).orElseThrow(() -> new CustomWebException("댓글을 찾을 수 없습니다."));
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


    public CommentRespDtoWeb getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomWebException("not found Comment"));
        return new CommentRespDtoWeb(comment);

    }
    @Transactional
    public void update(Long commentId, CommentReqDtoWebUpdate commentReqDtoWebUpdate) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomWebException("not found Comment"));
        comment.changeContent(commentReqDtoWebUpdate);
    }
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.deleteComment();
    }
}
