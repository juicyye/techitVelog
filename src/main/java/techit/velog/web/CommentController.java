package techit.velog.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techit.velog.domain.comment.service.CommentService;
import techit.velog.global.dto.PrincipalDetails;

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
                                @ModelAttribute("comment") CommentReqDtoWebBasic commentReqDtoWebBasic, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (bindingResult.hasErrors()) {
            return "redirect:/{blogName}/{postTitle}";
        }
        commentService.create(commentReqDtoWebBasic, principalDetails.getUsername());
        return "redirect:/{blogName}/{postTitle}";
    }

    @GetMapping("/{commentId}/modify")
    @PreAuthorize("hasRole('ADMIN') or @userService.getLoginIdByComment(#commentId) == principal.username")
    public String commentModifyForm(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle,
                                    @PathVariable("commentId") Long commentId, Model model) {
        CommentReqDtoWebUpdate comment = commentService.getComment(commentId);
        model.addAttribute("comment", comment);
        return "comment/modify";
    }

    @PostMapping("/{commentId}/modify")
    public String commentModify(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle,
                                @PathVariable("commentId") Long commentId, @ModelAttribute("content") CommentReqDtoWebUpdate commentReqDtoWebUpdate, RedirectAttributes rttr) {

        commentService.update(commentId, commentReqDtoWebUpdate);
        rttr.addAttribute("update", true);

        return "redirect:/{blogName}/{postTitle}";
    }

    @GetMapping("/{commentId}/delete")
    @PreAuthorize("hasRole('ADMIN') or @userService.getLoginIdByComment(#commentId) == principal.username")
    public String commentDelete(@PathVariable("blogName") String blogName, @PathVariable("postTitle") String postTitle,
                                @PathVariable("commentId") Long commentId, RedirectAttributes rttr) {
        commentService.deleteComment(commentId);
        rttr.addAttribute("delete", true);
        return "redirect:/{blogName}/{postTitle}";
    }
}
