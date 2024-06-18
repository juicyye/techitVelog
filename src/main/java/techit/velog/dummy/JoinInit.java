package techit.velog.dummy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.domain.user.service.UserService;

import static techit.velog.domain.user.dto.UserReqDto.*;

@Configuration
public class JoinInit extends DummyObject{

    @Bean
    @Profile("dev")
    CommandLineRunner init(UserService userService) {
        return args -> {
            Long join = userService.join(newUser());
        };
    }


}
