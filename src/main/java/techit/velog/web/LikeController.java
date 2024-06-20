package techit.velog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techit.velog.domain.liks.service.LikeService;
import techit.velog.domain.user.dto.UserReqDto;

import static techit.velog.domain.user.dto.UserReqDto.*;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/like/{blogName}/{postId}")
    public String like(@PathVariable("postId") Long postId, @AuthenticationPrincipal AccountDto accountDto, RedirectAttributes rttr) {
        String postTitle = likeService.like(postId, accountDto);
        rttr.addAttribute("postTitle", postTitle);

        return "redirect:/{blogName}/{postTitle}";

    }


}
