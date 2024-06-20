package techit.velog.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.comment.dto.CommentReqDto;
import techit.velog.domain.comment.dto.CommentRespDto;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.comment.repository.CommentRepository;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.repository.PostRepository;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.exception.CustomWebException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static techit.velog.domain.comment.dto.CommentReqDto.*;
import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(Long postId, CommentReqDtoWeb commentReqDtoWeb, AccountDto accountDto) {
        User user = userRepository.findByLoginId(accountDto.getLoginId()).orElseThrow(() -> new CustomWebException("유저를 찾을 수 없습니다."));
        Posts posts = postRepository.findById(postId).orElseThrow(() -> new CustomWebException("포스트를 찾을 수 없습니다."));
        Comment comment = new Comment(commentReqDtoWeb, posts, user);
        if (commentReqDtoWeb.getCommentId() != null) {
            Comment parent = commentRepository.findById(commentReqDtoWeb.getCommentId()).orElseThrow(() -> new CustomWebException("댓글을 찾을 수 없습니다."));
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


}
