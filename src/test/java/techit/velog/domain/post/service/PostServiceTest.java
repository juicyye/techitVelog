package techit.velog.domain.post.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.post.dto.PostReqDto;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.repository.PostRepository;
import techit.velog.domain.posttag.entity.PostTag;
import techit.velog.domain.tag.entity.Tags;
import techit.velog.domain.tag.repository.TagRepository;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.domain.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static techit.velog.domain.post.dto.PostReqDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void setup(){
        UserJoinReq userJoinReq = new UserJoinReq();
        userJoinReq.setPassword("test");
        userJoinReq.setName("name");
        userJoinReq.setEmail("123@gmail.com");
        userJoinReq.setLoginId("test");
        userJoinReq.setRole(Role.ROLE_ADMIN);
        userService.join(userJoinReq);
    }

    @Test
    void createPost_test() throws Exception {
        // given
        AccountDto accountDto = new AccountDto();
        accountDto.setLoginId("test");
        accountDto.setPassword("test");
        accountDto.setRole(Role.ROLE_ADMIN);

        PostReqDtoWeb postReqDtoWeb = new PostReqDtoWeb();
        postReqDtoWeb.setTitle("title");
        postReqDtoWeb.setContent("content");
        postReqDtoWeb.setReql(true);
        postReqDtoWeb.setIsSecret(IsSecret.SECRET);
        postReqDtoWeb.setTagName("하이하이 방가방가, 내이름은 태그");


        // when
        Long savedPost = postService.create(postReqDtoWeb, accountDto);


        // then
        Posts posts = postRepository.findById(1L).get();
        List<PostTag> postTags = posts.getPostTags();
        Assertions.assertThat(postTags.size()).isEqualTo(4);
        Assertions.assertThat(savedPost).isEqualTo(1L);
    }

    @Test
    void createTag_test(){
        // given
        AccountDto accountDto = new AccountDto();
        accountDto.setLoginId("test");
        accountDto.setPassword("test");
        accountDto.setRole(Role.ROLE_ADMIN);

        PostReqDtoWeb postReqDtoWeb = new PostReqDtoWeb();
        postReqDtoWeb.setTitle("title");
        postReqDtoWeb.setContent("content");
        postReqDtoWeb.setReql(true);
        postReqDtoWeb.setIsSecret(IsSecret.SECRET);
        postReqDtoWeb.setTagName("하이하이 방가방가, 내이름은 태그");


        // when
        Long savedPost = postService.create(postReqDtoWeb, accountDto);


        // then
        Tags tags = tagRepository.findById(1L).orElse(null);
        System.out.println("tags = " + tags.getName());
        Assertions.assertThat(tags).isNotNull();
    }


}