package techit.velog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import techit.velog.domain.blog.dto.BlogRespDto;
import techit.velog.domain.blog.service.BlogService;
import techit.velog.domain.post.dto.PostRespDto;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.tag.dto.TagRespDto;
import techit.velog.domain.tag.service.TagService;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.post.dto.PostRespDto.*;
import static techit.velog.domain.tag.dto.TagRespDto.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{id}")
public class BlogController {
    private final BlogService blogService;
    private final TagService tagService;
    private final PostService postService;

    @GetMapping
    public String blog(@PathVariable("id") String blogName, Model model) {
        List<BlogRespDtoWeb> blogs = blogService.getPost(blogName);
        List<PostRespDtoWeb> posts = postService.getPostByBlog(blogName);
        List<TagRespDtoWeb> tags = tagService.getTagAllByBlogName(blogName);
        model.addAttribute("blogs", blogs);
        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);

        return "blog/blog";
    }

    @GetMapping("/posts")
    public String posts(@PathVariable("id") String blogName, Model model) {
        List<PostRespDtoWeb> postByBlog = postService.getPostByBlog(blogName);
        return null;
    }


}
