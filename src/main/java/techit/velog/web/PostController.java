package techit.velog.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import techit.velog.domain.blog.dto.BlogRespDto;
import techit.velog.domain.blog.service.BlogService;
import techit.velog.domain.comment.entity.IsDeleted;
import techit.velog.domain.post.dto.PostRespDto;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.post.service.PostService;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.post.dto.PostReqDto.*;
import static techit.velog.domain.post.dto.PostRespDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final BlogService blogService;


    @ModelAttribute("isSecret")
    public IsSecret[] isSecrets(){
        return IsSecret.values();
    }

    @GetMapping("/create")
    public String postCreateForm(@ModelAttribute("post") PostReqDtoWeb postReqDtoWeb) {
        return "posts/create";
    }

    @PostMapping("/create")
    public String postCreate(@Validated @ModelAttribute("post") PostReqDtoWeb postReqDtoWeb, BindingResult bindingResult, @AuthenticationPrincipal AccountDto accountDto) {
        if(bindingResult.hasErrors()) {
            log.info("create post error {}", bindingResult.getAllErrors());
            return "posts/create";
        }
        postService.create(postReqDtoWeb, accountDto);
        return "redirect:/";
    }

    @GetMapping
    public String posts(@PageableDefault(sort = "createDate", direction = Sort.Direction.ASC)Pageable pageable, Model model,@AuthenticationPrincipal AccountDto accountDto) {
        BlogRespDtoWeb blog = null;
        if (accountDto != null) {
            blog = blogService.getBlog(accountDto);
        }
        Page<PostRespDtoWebAll> posts = postService.getPosts(pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("blog", blog);
        return "posts/list";
    }





}
