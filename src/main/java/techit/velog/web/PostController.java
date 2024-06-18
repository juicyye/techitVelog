package techit.velog.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import techit.velog.domain.comment.entity.IsDeleted;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.post.service.PostService;

import static techit.velog.domain.post.dto.PostReqDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;


    @ModelAttribute("isSecret")
    public IsSecret[] isSecrets(){
        return IsSecret.values();
    }

    @GetMapping("/create")
    public String postCreateForm(@ModelAttribute("post") PostReqDtoWeb postReqDtoWeb) {
        return "posts/create";
    }

    /**
     *
     */

    @PostMapping("/create")
    public String postCreate(@Validated @ModelAttribute("post") PostReqDtoWeb postReqDtoWeb, BindingResult bindingResult, @AuthenticationPrincipal AccountDto accountDto) {
        if(bindingResult.hasErrors()) {
            log.info("create post error {}", bindingResult.getAllErrors());
            return "posts/create";
        }
        postService.create(postReqDtoWeb, accountDto);
        return "redirect:/";
    }





}
