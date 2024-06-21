package techit.velog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import techit.velog.domain.blog.service.BlogService;
import techit.velog.domain.comment.dto.CommentRespDto;
import techit.velog.domain.comment.service.CommentService;
import techit.velog.domain.follow.dto.FollowRespDto;
import techit.velog.domain.follow.service.FollowService;
import techit.velog.domain.liks.service.LikeService;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.tag.service.TagService;
import techit.velog.domain.user.dto.UserReqDto;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.follow.dto.FollowRespDto.*;
import static techit.velog.domain.post.dto.PostRespDto.*;
import static techit.velog.domain.tag.dto.TagRespDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{blogName}")
public class BlogController {
    private final BlogService blogService;
    private final TagService tagService;
    private final PostService postService;
    private final FollowService followService;
    private final LikeService likeService;
    private final CommentService commentService;

    @GetMapping
    public String blog(@PathVariable("blogName") String blogName, Model model) {
        BlogRespDtoWeb blog = blogService.getPost(blogName);
        List<PostRespDtoWeb> posts = postService.getAllByBlogName(blogName);
        List<TagRespDtoWeb> tags = tagService.getTagAllByBlogName(blogName);
        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);

        return "blog/blog";
    }

    @GetMapping("/{postTitle}")
    public String postDetail(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle, Model model) {
        PostRespDtoWeb postRespDtoWeb = postService.getPostByBlogName(blogName, postTitle);
        List<CommentRespDtoWeb> comments = commentService.getComments(postRespDtoWeb.getPostId());
        model.addAttribute("post", postRespDtoWeb);
        model.addAttribute("comments", comments);

        return "blog/post";
    }

    @GetMapping("/follow")
    public String follow(@PathVariable("blogName") String blogName, @AuthenticationPrincipal AccountDto accountDto) {
        followService.follow(accountDto.getLoginId(),blogName);

        return "redirect:/{blogName}";
    }

    @GetMapping("/followers")
    public String followers(@PathVariable("blogName") String blogName, Model model) {
        List<FollowRespDtoWeb> followRespDtoWebs = followService.findByFollowers(blogName);
        model.addAttribute("follows", followRespDtoWebs);
        return "blog/follow";
    }

    @GetMapping("/followings")
    public String followings(@PathVariable("blogName") String blogName, Model model) {
        List<FollowRespDtoWeb> followRespDtoWebs = followService.findByFollowings(blogName);
        model.addAttribute("follows", followRespDtoWebs);
        return "blog/follow";
    }

    @GetMapping("/likes")
    public String likes(@PathVariable("blogName") String blogName, Model model) {
        List<PostRespDtoWeb> postRespDtoWebs = likeService.getLikes(blogName);
        model.addAttribute("posts", postRespDtoWebs);
        return "blog/likes";
    }


}
