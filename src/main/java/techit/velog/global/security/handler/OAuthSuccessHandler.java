package techit.velog.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import org.springframework.stereotype.Component;
import techit.velog.global.dto.PrincipalDetails;
import techit.velog.global.security.jwt.JWTVO;
import techit.velog.global.security.jwt.JwtProcess;

import java.io.IOException;


@Component
@Slf4j
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProcess jwtProcess;

    public OAuthSuccessHandler(JwtProcess jwtProcess) {
        this.jwtProcess = jwtProcess;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        setDefaultTargetUrl("http://localhost:8080/");
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();


        String token = jwtProcess.createJwt(principal.getAccountDto());
        log.info("success token : {}",token);
        response.addCookie(createCookie(JWTVO.HEADER, token));
        response.sendRedirect("http://localhost:8080/");
    }
    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
