package techit.velog.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.dto.UserRespDtoWeb;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.service.UserService;

import java.util.List;

import static techit.velog.domain.user.dto.UserReqDto.*;
import static techit.velog.domain.user.dto.UserRespDtoWeb.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public String users(Model model, @PageableDefault(size = 20) Pageable pageable) {
        Page<UserRespDtoWebAdmin> users = userService.getUsers(pageable);
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/user/{id}/modify")
    public String userModifyForm(@PathVariable("id") Long userId, Model model) {
        UserRespDtoWebAdmin user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "admin/userModify";
    }

    @PostMapping("/user/{id}/modify")
    public String userModify(@PathVariable("id") Long userId, @Valid @ModelAttribute("user") UserReqDtoWebAdmin user, BindingResult bindingResult, RedirectAttributes rttr) {
        if(bindingResult.hasErrors()) {
            return "admin/userModify";
        }
        userService.updateByAdmin(userId, user);
        rttr.addAttribute("update", true);
        return "redirect:/admin/users";
    }

    @PostMapping("/user/{id}/delete")
    public String userDelete(@PathVariable("id") Long userId, RedirectAttributes rttr) {
        userService.deleteUser(userId);
        rttr.addAttribute("delete", true);
        return "redirect:/admin/users";
    }
}
