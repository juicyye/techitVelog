package techit.velog.global.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import techit.velog.global.dto.PrincipalDetails;
import techit.velog.global.security.details.FormWebAuthenticationDetails;
import techit.velog.global.security.exception.SecretException;

@RequiredArgsConstructor
@Component("authenticationProvider")
public class FormAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = authentication.getCredentials().toString();
        PrincipalDetails principalDetails = (PrincipalDetails) userDetailsService.loadUserByUsername(loginId);
        if(!passwordEncoder.matches(password, principalDetails.getPassword())) {
            throw new BadCredentialsException("bad credentials");
        }
        String secretKey = ((FormWebAuthenticationDetails) authentication.getDetails()).getSecretKey();
        if(secretKey == null || !secretKey.equals("secret")) {
            throw new SecretException("secret key is incorrect");
        }
        return new UsernamePasswordAuthenticationToken(principalDetails.getAccountDto(),null, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
