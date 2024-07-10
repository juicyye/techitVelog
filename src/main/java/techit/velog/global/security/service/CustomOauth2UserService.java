package techit.velog.global.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.dto.*;

import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User: {}", oAuth2User);
        log.info("userRequest: {}", userRequest.getClientRegistration());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if(registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else{
            return null;
        }
        String email = oAuth2Response.getEmail();
        Optional<User> _user = userRepository.findByEmail(email);
        if (_user.isEmpty()) {

            User savedUser = userRepository.save(new User(oAuth2Response));
            blogRepository.saveAndFlush(new Blog("@" + savedUser.getName(), savedUser));
            return new PrincipalDetails(new AccountDto(savedUser), oAuth2Response.getAttributes());
        } else{
            User user = _user.get();
            user.changeOauth(oAuth2Response);

            return new PrincipalDetails(new AccountDto(user), oAuth2Response.getAttributes());
        }

    }
}
