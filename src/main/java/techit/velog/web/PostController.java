package techit.velog.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import techit.velog.domain.blog.service.BlogService;
import techit.velog.domain.post.dto.PostSearch;
import techit.velog.domain.post.dto.PostSortType;
import techit.velog.domain.post.service.PostService;
import techit.velog.domain.tag.service.TagService;
import techit.velog.domain.user.service.UserService;
import techit.velog.global.dto.PrincipalDetails;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDtoWeb.*;
import static techit.velog.domain.post.dto.PostReqDtoWeb.*;
import static techit.velog.domain.post.dto.PostRespDtoWeb.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final BlogService blogService;
    private final TagService tagService;
    private final UserService userService;


    @GetMapping("/create")
    public String postCreateForm(@ModelAttribute("post") PostReqDtoWebCreate postReqDtoWebCreate) {
        return "posts/create";
    }

    @PostMapping("/create")
    public String postCreate(@Validated @ModelAttribute("post") PostReqDtoWebCreate postReqDtoWebCreate, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (bindingResult.hasErrors()) {
            log.info("create post error {}", bindingResult.getAllErrors());
            return "posts/create";
        }
        if (postService.duplicateBlogName(postReqDtoWebCreate, principalDetails.getUsername())) {
            bindingResult.reject("duplicate-blog-name", "동일한 제목은 사용하실 수 없습니다.");
            return "posts/create";
        }
        postService.create(postReqDtoWebCreate, principalDetails.getUsername());
        return "redirect:/";
    }

    @GetMapping
    public String posts(Pageable pageable, Model model,
                        @CurrentSecurityContext SecurityContext securityContext, @RequestParam(value = "sortType",required = false,defaultValue = "NEWEST") String sortType,
                        @ModelAttribute PostSearch search) {
        log.info("securityContext {}", securityContext);
        log.info("search {}", search);
        BlogRespDtoWebBasic blog = null;
        if (userService.isUser(securityContext)) {
            log.info("principal {} ",securityContext.getAuthentication().getPrincipal());
            blog = blogService.getBlog((PrincipalDetails)securityContext.getAuthentication().getPrincipal());
        }

        Page<PostRespDtoWebAll> posts = postService.getPosts(pageable, PostSortType.valueOf(sortType.toUpperCase()),search);
        model.addAttribute("posts", posts);
        model.addAttribute("blog", blog);
        return "posts/list";
    }

    @GetMapping("/saves")
    public String savePost(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        List<PostRespDtoWebSave> postSave = postService.getPostSave(principalDetails.getUsername());
        model.addAttribute("posts", postSave);
        return "posts/saves";
    }


}
