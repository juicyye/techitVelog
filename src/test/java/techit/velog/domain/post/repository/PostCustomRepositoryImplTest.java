package techit.velog.domain.post.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.dto.BlogRespDto;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.blog.service.BlogService;
import techit.velog.domain.post.dto.PostReqDto;
import techit.velog.domain.post.dto.PostRespDto;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.domain.user.service.UserService;
import techit.velog.dummy.DummyObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.post.dto.PostReqDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PostCustomRepositoryImplTest extends DummyObject {

    @Autowired
    private PostCustomRepositoryImpl postCustomRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private PostService postService;

    @BeforeEach
    void setup(){
        Long join = userService.join(newUser());
        AccountDto accountDto = new AccountDto(1L, "user", "user", Role.ROLE_USER);
        Blog blog = blogRepository.findByTitle("@user").orElseThrow();
        PostReqDtoWeb postReqDtoWeb = new PostReqDtoWeb("타이틀", "콘텐트");
        Posts posts = new Posts(postReqDtoWeb, blog);
        postService.create(postReqDtoWeb, accountDto);
    }

    @Test
    void post_test() throws Exception {
        // given
        List<PostRespDto.PostRespDtoWebAll> test = postCustomRepository.test();
        System.out.println("test = " + test);

        // when

        // then
    }

}