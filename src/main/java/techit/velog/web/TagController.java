package techit.velog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import techit.velog.domain.blog.service.BlogService;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.tag.service.TagService;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.post.dto.PostRespDtoWeb.*;
import static techit.velog.domain.tag.dto.TagRespDto.*;

@Controller
@RequiredArgsConstructor
public class TagController {
    private final PostService postService;
    private final TagService tagService;
    private final BlogService blogService;

    @GetMapping("/{blogName}/posts/{tagName}")
    public String getTagPost(@PathVariable("blogName") String blogName, @PathVariable("tagName") String tagName, Model model, @CurrentSecurityContext SecurityContext securityContext) {
        // 결국 가져오는건 포스트 하지만 일대다는 안되니까 포스트태그에서 시작하는 수밖에 없다
        List<PostRespDtoWebVelog> posts = postService.getPostsTagName(blogName, tagName, securityContext);
        BlogRespDtoWeb blog = blogService.getBlog(blogName);
        List<TagRespDtoWeb> tags = tagService.getTagAllByBlogName(blogName);
        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);



        return "blog/blog";
    }
}
