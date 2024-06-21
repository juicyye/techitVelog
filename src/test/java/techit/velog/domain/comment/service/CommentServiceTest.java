package techit.velog.domain.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.comment.dto.CommentReqDto;
import techit.velog.domain.comment.dto.CommentRespDto;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.comment.repository.CommentRepository;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.repository.PostRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static techit.velog.domain.comment.dto.CommentReqDto.*;
import static techit.velog.domain.comment.dto.CommentRespDto.*;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void commentService_test() throws Exception {
        // given
        Posts posts = new Posts("타이틀", "내용");
        Posts savedPost = postRepository.save(posts);
        Comment parent = new Comment("댓글",posts);
        commentRepository.save(parent);
        Comment child = new Comment("대댓글", parent,posts);
        commentRepository.save(child);


        Posts posts1 = postRepository.findById(1L).orElseThrow();
        List<Comment> comments1 = posts1.getComments();
        for (Comment comment : comments1) {
            List<Comment> child1 = comment.getChild();
            System.out.println("comment = " + comment.getContent());
            for (Comment comment1 : child1) {
                System.out.println("comment1.getContent() = " + comment1.getParent().getId());
            }
        }
        // when
        List<CommentRespDtoWeb> comments = commentService.getComments(1L);
        for (CommentRespDtoWeb commentRespDtoWeb : comments) {
            List<CommentRespDtoWeb> child1 = commentRespDtoWeb.getChild();
            for (CommentRespDtoWeb respDtoWeb : child1) {
                System.out.println("respDtoWeb = " + respDtoWeb.getContent());
            }
        }

        // then
    }

    @Test
    void commentRepository_test() throws Exception {
        // given
        Posts posts = new Posts("타이틀", "내용");
        Posts savedPost = postRepository.save(posts);
        Comment parent = new Comment("댓글",posts);
        commentRepository.save(parent);
        Comment child = new Comment("대댓글", parent,posts);
        commentRepository.save(child);


        // when
        List<Comment> comments = commentRepository.findAllByPost(1L);
        for (Comment comment : comments) {
            System.out.println("comment.getId() = " + comment.getId());
            System.out.println("comment.getContent() = " + comment.getContent());
            List<Comment> child1 = comment.getChild();
            for (Comment comment1 : child1) {
                System.out.println("comment1.getContent() = " + comment1.getContent());
                System.out.println("comment1.getParent().getId() = " + comment1.getParent().getId());
            }
        }

        // then
    }

}