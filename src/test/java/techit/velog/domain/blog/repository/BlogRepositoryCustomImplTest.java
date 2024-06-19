package techit.velog.domain.blog.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.dto.BlogRespDto;
import techit.velog.domain.post.dto.PostReqDto;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.service.UserService;
import techit.velog.dummy.DummyObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static techit.velog.domain.blog.dto.BlogRespDto.*;

@Transactional
@SpringBootTest
class BlogRepositoryCustomImplTest extends DummyObject {

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @BeforeEach
    void setup(){
        userService.join(adminUser());
    }

    @Test
    void Blog_test() throws Exception {
        // given
//        UserReqDto.AccountDto accountDto = new UserReqDto.AccountDto();
//        accountDto.setLoginId("admin");
//        accountDto.setPassword("admin");
//        accountDto.setRole(Role.ROLE_ADMIN);
//
//        for (int i = 0; i < 10; i++) {
//            PostReqDto.PostReqDtoWeb postReqDtoWeb = new PostReqDto.PostReqDtoWeb("title" + i, "content" + i);
//            postService.create(postReqDtoWeb, accountDto);
//        }
//
//        // when
//        List<BlogRespDtoWeb> blog = blogRepository.findAllByBlog("@adminUser");
//        for (BlogRespDtoWeb blogRespDtoWeb : blog) {
//            System.out.println("blogRespDtoWeb.getUserName() = " + blogRespDtoWeb.getNickname());
//        }

        // then

    }


}