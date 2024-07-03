package techit.velog.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import techit.velog.domain.user.service.UserService;

@Configuration
public class JoinInit extends DummyObject{

    @Bean
    @Profile("dev")
    CommandLineRunner init(UserService userService) {
        return args -> {
            Long join = userService.join(adminUser());
            Long join1 = userService.join(newUser());
        };
    }
}
