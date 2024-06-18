package techit.velog.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import techit.velog.domain.user.dto.UserReqDto;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;

import java.time.LocalDateTime;

import static techit.velog.domain.user.dto.UserReqDto.*;

public class DummyObject {
    protected UserJoinReq newUser(){
        UserJoinReq userJoinReq = new UserJoinReq();
        userJoinReq.setRole(Role.ROLE_ADMIN);
        userJoinReq.setPassword("test");
        userJoinReq.setEmail("test@naver.com");
        userJoinReq.setEmailCheck(true);
        userJoinReq.setPasswordConfirm("test");
        userJoinReq.setLoginId("test");
        userJoinReq.setName("user");
        return userJoinReq;
    }

    protected User newMockUser(Long id, String username, String fullname){
        return null;

    }

}
