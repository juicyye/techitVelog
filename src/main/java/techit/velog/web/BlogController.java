package techit.velog.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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
import techit.velog.domain.post.dto.PostSearch;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.tag.service.TagService;
import techit.velog.global.dto.PrincipalDetails;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDtoWeb.*;
import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.follow.dto.FollowRespDto.*;
import static techit.velog.domain.post.dto.PostReqDtoWeb.*;
import static techit.velog.domain.post.dto.PostRespDtoWeb.*;
import static techit.velog.domain.tag.dto.TagRespDto.*;

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
    public String blog(@PathVariable("blogName") String blogName, Model model, @CurrentSecurityContext SecurityContext securityContext, @ModelAttribute("search")PostSearch postSearch) {
        BlogRespDtoWebBasic blog = blogService.getBlog(blogName);
        List<PostRespDtoWebVelog> posts = postService.getPostsVelog(blogName, securityContext, postSearch);
        List<TagRespDtoWeb> tags = tagService.getTagAllByBlogName(blogName);
        model.addAttribute("blog", blog);
        model.addAttribute("posts", posts);
        model.addAttribute("tags", tags);

        return "blog/blog";
    }

    @GetMapping("/{postTitle}")
    // todo 로그아웃햇을때 댓글이 제대로 안보이는 현상발생
    public String postDetail(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle, Model model,
                             HttpServletRequest request, HttpServletResponse response, @CurrentSecurityContext SecurityContext securityContext) {
        PostRespDtoWebDetail postRespDtoWebDetail = postService.getPostDetails(blogName, postTitle, securityContext);
        PostRespDtoWebNextAndPrevious nextPost = postService.getNextPost(postRespDtoWebDetail, securityContext);
        PostRespDtoWebNextAndPrevious previousPost = postService.getPreviousPost(postRespDtoWebDetail, securityContext);
        List<CommentRespDtoWeb> comments = commentService.getComments(postRespDtoWebDetail.getPostId());
        postService.viewCountValidation(blogName, postTitle, request, response);
        model.addAttribute("post", postRespDtoWebDetail);
        model.addAttribute("nextPost", nextPost);
        model.addAttribute("previousPost", previousPost);
        for (CommentRespDtoWeb comment : comments) {
            System.out.println("comment.getContent() = " + comment.getContent());
        }
        model.addAttribute("comments", comments);
        return "blog/post";
    }

    @GetMapping("/follow")
    public String follow(@PathVariable("blogName") String blogName, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        followService.follow(principalDetails.getUsername(),blogName);
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
    public String likes(@PathVariable("blogName") String blogName, Model model, Pageable pageable) {
        Page<PostRespDtoWebAll> postRespDtoWebDetails = likeService.getLikes(blogName, pageable);
        model.addAttribute("posts", postRespDtoWebDetails);
        return "posts/likePosts";
    }

    @GetMapping("/{postName}/postModify/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getLoginIdByPost(#postId) == principal.username")
    public String postModifyForm(@PathVariable("postId") Long postId, Model model) {
        PostRespDtoWebUpdate post = postService.getUpdatePost(postId);
        model.addAttribute("post", post);
        return "posts/modify";
    }

    @PostMapping("/{postTitle}/postModify/{postId}")
    public String postModify(@PathVariable("postId") Long postId, @ModelAttribute("post") PostReqDtoWebUpdate postReqDtoWebUpdate, BindingResult bindingResult,RedirectAttributes rttr) {
        if (bindingResult.hasErrors()) {
            return "posts/modify";
        }
        postService.update(postId, postReqDtoWebUpdate);
        // todo 이게 정말 최선일까 생각해보기
        tagService.removeTag(postReqDtoWebUpdate.getBlogId());
        rttr.addAttribute("update",true);
        return "redirect:/{blogName}/{postTitle}";
    }

    @PostMapping("/postDelete/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getLoginIdByPost(#postId) == principal.username")
    public String postDelete(@PathVariable("postId") Long postId, RedirectAttributes rttr) {
        Long blogId = postService.delete(postId);
        tagService.removeTag(blogId);
        rttr.addAttribute("delete", true);
        return "redirect:/{blogName}";
    }
}
