package techit.velog.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import techit.velog.global.dto.AccountDto;
import techit.velog.global.dto.PrincipalDetails;

import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtProcess jwtProcess;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProcess jwtProcess) {
        super(authenticationManager);
        this.jwtProcess = jwtProcess;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = isCookieVerify(request);
        if (token != null) {
            if (!jwtProcess.isExpired(token)) {
                String loginId = jwtProcess.getLoginId(token);
                String role = jwtProcess.getRole(token);
                PrincipalDetails principalDetails = new PrincipalDetails(new AccountDto(loginId, role));
                Authentication authToken = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }

    private String isCookieVerify(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JWTVO.HEADER)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
