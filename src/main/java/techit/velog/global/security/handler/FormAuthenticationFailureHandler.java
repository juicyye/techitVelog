package techit.velog.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import techit.velog.global.security.exception.SecretException;

import java.io.IOException;

@Component("failureHandler")
public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = "Invalid LoginId or password";
        if (exception instanceof BadCredentialsException) {
            message = "Invalid LoginId or password";
        } else if (exception instanceof UsernameNotFoundException) {
            message = "Invalid LoginId or password";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "password expired";
        } else if (exception instanceof SecretException) {
            message = "secret key problem";
        }
        setDefaultFailureUrl("/login?error=true&exception="+message);
        super.onAuthenticationFailure(request, response, exception);
    }
}
