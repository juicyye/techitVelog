package techit.velog.web;

import jakarta.servlet.http.Cookie;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techit.velog.domain.user.dto.UserRespDtoWeb;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.service.UserService;
import techit.velog.global.dto.PrincipalDetails;
import techit.velog.global.security.jwt.JWTVO;

import static techit.velog.domain.user.dto.UserReqDto.*;
import static techit.velog.domain.user.dto.UserRespDtoWeb.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @ModelAttribute("roles")
    public Role[] roles(){
        return Role.values();
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
        /*if (userService.validationEmail(userJoinReq.getEmail())) {
            bindingResult.reject("validated_email", "이미 등록된 이메일이 있습니다.");
            return "login/join";
        }
        if (userService.validationLoginId(userJoinReq.getLoginId())) {
            bindingResult.reject("validated_loginId", "이미 등록된 아이디가 있습니다.");
            return "login/join";
        }
        if(userService.validationName(userJoinReq.getName())) {
            bindingResult.reject("validated_name", "이미 존재하는 블로그이름 또는 이름입니다.");
            return "user/update";
        }*/
        if (!userJoinReq.getPassword().equals(userJoinReq.getPasswordConfirm())) {
            bindingResult.reject("password_not_confirm","비밀번호가 일치하지 않습니다.");
            return "login/join";
        }

        userService.join(userJoinReq);
        return "redirect:/";
    }

    /*@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }

        return "redirect:/";
    }*/

    @GetMapping("/denied")
    public String denied(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login/denied";
    }

    @GetMapping("/account")
    public String account(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserRespWebInfo user = userService.getUser(principalDetails.getUsername());
        model.addAttribute("blog", user);
        return "user/info";
    }

    @GetMapping("/account/update")
    public String accountUpdateForm(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        UserRespDtoWebUpdate user = userService.getUserByUpdate(principalDetails.getUsername());
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/account/update")
    public String update(@Validated @ModelAttribute("user") UserReqDtoWebUpdate userReqDtoWeb, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails, RedirectAttributes rttr) {
        if(bindingResult.hasErrors()) {
            log.info("update error {}",bindingResult.getAllErrors());
            return "user/update";
        }
        if (!userService.checkPassword(principalDetails.getUsername(),userReqDtoWeb.getPassword())) {
            bindingResult.reject("invalid_password","비밀번호가 일치하지 않습니다.");
            return "user/update";
        }
        if (!userReqDtoWeb.getChangePassword().equals(userReqDtoWeb.getChangePasswordConfirm())) {
            bindingResult.reject("password_not_confirm","비밀번호가 일치하지 않습니다.");
            return "user/update";
        }

        userService.updateInfo(userReqDtoWeb,principalDetails.getUsername());
        rttr.addAttribute("update",true);
        return "redirect:/";
    }

    @GetMapping("/account/delete")
    public String deleteForm(@ModelAttribute("user") UserReqDeleteDto userReqDeleteDto) {
        return "user/delete";
    }

    @PostMapping("/account/delete")
    public String delete(@ModelAttribute("user") UserReqDeleteDto userReqDeleteDto, BindingResult bindingResult
            , @AuthenticationPrincipal PrincipalDetails principalDetails, RedirectAttributes rttr, HttpServletRequest request, HttpServletResponse response) {
        if(!userService.checkPassword(principalDetails.getUsername(),userReqDeleteDto.getPassword())) {
            bindingResult.reject("invalid_password","비밀번호가 일치하지 않습니다.");
            return "user/delete";
        }
        userService.deleteUser(principalDetails.getUsername());
        rttr.addAttribute("delete",true);
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }


}
