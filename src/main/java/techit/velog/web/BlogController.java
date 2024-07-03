package techit.velog.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techit.velog.domain.blog.service.BlogService;
import techit.velog.domain.comment.service.CommentService;
import techit.velog.domain.follow.service.FollowService;
import techit.velog.domain.liks.service.LikeService;
import techit.velog.domain.post.dto.PostReqDtoWeb;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.tag.service.TagService;
import techit.velog.domain.uploadfile.FileStore;

import java.net.MalformedURLException;
import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.follow.dto.FollowRespDto.*;
import static techit.velog.domain.post.dto.PostRespDtoWeb.*;
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
    public String blog(@PathVariable("blogName") String blogName, Model model, @CurrentSecurityContext SecurityContext securityContext) {
        BlogRespDtoWeb blog = blogService.getBlog(blogName);
        List<PostRespDtoWebVelog> posts = postService.getPostsVelog(blogName, securityContext);
        List<TagRespDtoWeb> tags = tagService.getTagAllByBlogName(blogName);
        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);

        return "blog/blog";
    }

    @GetMapping("/{postTitle}")
    public String postDetail(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle, Model model,
                             HttpServletRequest request, HttpServletResponse response) {
        PostRespDtoWebDetail postRespDtoWebDetail = postService.getPostDetails(blogName, postTitle);
        List<CommentRespDtoWeb> comments = commentService.getComments(postRespDtoWebDetail.getPostId());
        postService.viewCountValidation(blogName, postTitle, request, response);
        model.addAttribute("post", postRespDtoWebDetail);
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
        List<PostRespDtoWebAll> postRespDtoWebDetails = likeService.getLikes(blogName);
        model.addAttribute("posts", postRespDtoWebDetails);
        return "posts/list";
    }

    @GetMapping("/{postName}/postModify/{postId}")
    public String postModifyForm(@PathVariable("postId") Long postId, Model model) {
        PostRespDtoWebUpdate post = postService.getUpdatePost(postId);
        model.addAttribute("post", post);
        return "posts/modify";
    }

    @PostMapping("/{postTitle}/postModify/{postId}")
    public String postModify(@PathVariable("postId") Long postId, @ModelAttribute("post") PostReqDtoWeb.PostReqDtoWebUpdate postReqDtoWebUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "posts/modify";
        }
        postService.update(postId, postReqDtoWebUpdate);
        // todo 이게 정말 최선일까 생각해보기
        tagService.removeTag(postReqDtoWebUpdate.getBlogId());
        return "redirect:/{blogName}/{postTitle}";
    }

    @GetMapping("/postDelete/{postId}")
    public String postDelete(@PathVariable("postId") Long postId, RedirectAttributes rttr) {
        Long blogId = postService.delete(postId);
        tagService.removeTag(blogId);
        rttr.addAttribute("deleted", true);
        return "redirect:/{blogName}";
    }



}
