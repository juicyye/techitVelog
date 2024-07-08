package techit.velog.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import techit.velog.domain.user.dto.UserRespDtoWeb;
import techit.velog.domain.user.service.UserService;

import java.util.List;

import static techit.velog.domain.user.dto.UserRespDtoWeb.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/users")
    public String users(Model model) {
        List<UserRespDtoWebAdmin> users = userService.getUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/user/{id}/modify")
    public String userModifyForm(@PathVariable("id") Long userId, Model model) {
        UserRespDtoWebAdmin user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "admin/userModify";
    }
}
