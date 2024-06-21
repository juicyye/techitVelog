package techit.velog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import techit.velog.domain.comment.dto.CommentReqDto;
import techit.velog.domain.comment.service.CommentService;
import techit.velog.domain.user.dto.UserReqDto;

import static techit.velog.domain.comment.dto.CommentReqDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{blogName}/{postTitle}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public String createComment (@PathVariable("blogName") String blogName,@PathVariable("postTitle") String postTitle
            , @ModelAttribute("comment") CommentReqDtoWeb commentReqDtoWeb, BindingResult bindingResult, @AuthenticationPrincipal AccountDto accountDto) {
        if(bindingResult.hasErrors()) {
            return "redirect:/{blogName}/{postTitle}";
        }
        commentService.create(commentReqDtoWeb, accountDto);
        return "redirect:/{blogName}/{postTitle}";
    }
}
