package techit.velog.global.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import techit.velog.global.security.handler.FormAccessDeniedHandler;
import techit.velog.global.security.handler.FormAuthenticationFailureHandler;
import techit.velog.global.security.handler.FormAuthenticationSuccessHandler;
import techit.velog.global.security.handler.OAuthSuccessHandler;
import techit.velog.global.security.jwt.JWTVO;
import techit.velog.global.security.jwt.JwtAuthorizationFilter;
import techit.velog.global.security.jwt.JwtProcess;
import techit.velog.global.security.provider.FormAuthenticationProvider;
import techit.velog.global.security.service.CustomOauth2UserService;
import techit.velog.global.security.service.FormUserDetailsService;

import java.util.Collections;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final FormUserDetailsService formUserDetailsService;
    private final FormAuthenticationSuccessHandler successHandler;
    private final FormAuthenticationFailureHandler failureHandler;
    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    private final CustomOauth2UserService oauth2UserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final JwtProcess jwtProcess;
    private final AuthenticationConfiguration configuration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("loginId")
                        .failureHandler(failureHandler)
                        .successHandler(successHandler)
                        .authenticationDetailsSource(authenticationDetailsSource)
                        .permitAll())
                .userDetailsService(formUserDetailsService)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(info -> info
                                .userService(oauth2UserService))
                        .successHandler(oAuthSuccessHandler))
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(configuration), jwtProcess), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new FormAccessDeniedHandler("denied")))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .addLogoutHandler((request, response, authentication) -> {
                            for (Cookie cookie : request.getCookies()) {
                                String cookieName = cookie.getName();
                                Cookie cookieToDelete = new Cookie(cookieName, null);
                                cookieToDelete.setMaxAge(0);
                                response.addCookie(cookieToDelete);
                            }
                        })
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/");
                        }));



        return http.build();
    }



    private CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setMaxAge(3600L);
        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }


}
