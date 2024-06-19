package techit.velog.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.dto.UserRespDto;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.service.UserService;

import static techit.velog.domain.user.dto.UserReqDto.*;
import static techit.velog.domain.user.dto.UserRespDto.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @ModelAttribute("roles")
    public Role[] roles(){
        return Role.values();
    }

    // todo 추후 삭제예정
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login/login";
    }


    @GetMapping("/join")
    public String joinForm(@ModelAttribute("user") UserJoinReq userJoinReq) {
        return "login/join";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("user") UserJoinReq userJoinReq, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            log.info("join error {}",bindingResult.getAllErrors());
            return "login/join";
        }
        if (userService.validationEmail(userJoinReq.getEmail())) {
            bindingResult.reject("vali_email", "이미 등록된 이메일이 있습니다.");
            return "login/join";
        }
        if (userService.validationLoginId(userJoinReq.getLoginId())) {
            bindingResult.reject("vali_loginId", "이미 등록된 아이디가 있습니다.");
            return "login/join";
        }
        if (!userJoinReq.getPassword().equals(userJoinReq.getPasswordConfirm())) {
            bindingResult.reject("password_not_confirm","비밀번호가 일치하지 않습니다.");
            return "login/join";
        }

        userService.join(userJoinReq);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }

    @GetMapping("/denied")
    public String denied(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login/denied";
    }

    @GetMapping("/account")
    public String account(Model model, @AuthenticationPrincipal AccountDto accountDto) {
        UserRespDtoWeb user = userService.getUser(accountDto.getLoginId());
        model.addAttribute("user", user);
        return "user/info";
    }
}
