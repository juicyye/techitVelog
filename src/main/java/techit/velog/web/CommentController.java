package techit.velog.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techit.velog.domain.comment.service.CommentService;

import static techit.velog.domain.comment.dto.CommentReqDtoWeb.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{blogName}/{postTitle}")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public String createComment(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle,
                                @ModelAttribute("comment") CommentReqDtoWebBasic commentReqDtoWebBasic, BindingResult bindingResult, @AuthenticationPrincipal AccountDto accountDto) {
        if (bindingResult.hasErrors()) {
            return "redirect:/{blogName}/{postTitle}";
        }
        commentService.create(commentReqDtoWebBasic, accountDto);
        return "redirect:/{blogName}/{postTitle}";
    }

    @GetMapping("/{commentId}/modify")
    public String commentModifyForm(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle,
                                @PathVariable("commentId") Long commentId, Model model) {
        CommentReqDtoWebUpdate comment = commentService.getComment(commentId);
        model.addAttribute("comment", comment);
        return "comment/modify";
    }

    @PostMapping("/{commentId}/modify")
    public String commentModify(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle,
                                @PathVariable("commentId") Long commentId, @RequestParam("content") String content, RedirectAttributes rttr) {

        boolean isUpdated = commentService.update(commentId, content);
        if (isUpdated) {
            rttr.addAttribute("update", true);
        }
        return "redirect:/{blogName}/{postTitle}";
    }

    @GetMapping("/{commentId}/delete")
    public String commentDelete(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle,
                                @PathVariable("commentId") Long commentId, RedirectAttributes rttr) {
        boolean isDeleted = commentService.deleteComment(commentId);
        if (isDeleted) {
            rttr.addAttribute("delete", true);
        }
        return "redirect:/{blogName}/{postTitle}";
    }
}
