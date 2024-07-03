package techit.velog.domain.post.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.blog.service.BlogService;
import techit.velog.domain.post.dto.PostRespDtoWeb;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.service.UserService;
import techit.velog.dummy.DummyObject;

import java.util.List;

import static techit.velog.domain.post.dto.PostReqDtoWeb.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PostsCustomRepositoryImplTest extends DummyObject {

    @Autowired
    private PostsCustomRepositoryImpl postCustomRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private PostsRepository postRepository;
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
        PostReqDtoWebCreate postReqDtoWebCreate = new PostReqDtoWebCreate("타이틀", "콘텐트");
        Posts posts = new Posts(postReqDtoWebCreate, blog);
        postService.create(postReqDtoWebCreate, accountDto);
    }

    @Test
    void post_test() throws Exception {
        List<Posts> all = postRepository.findAll();
        for (Posts posts : all) {
            System.out.println("posts.getTitle() = " + posts.getTitle());
            Blog blog = posts.getBlog();
            System.out.println("blog.getTitle() = " + blog.getTitle());
        }
        // given
        PostRespDtoWeb.PostRespDtoWebDetail result = postRepository.findPostDetail("@user", "타이틀");

        // when
        System.out.println("result = " + result);

        // then
    }

}