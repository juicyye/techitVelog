package techit.velog.dummy;

import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;

import static techit.velog.domain.user.dto.UserReqDto.*;

public class DummyObject {
    protected UserJoinReq adminUser(){
        UserJoinReq userJoinReq = new UserJoinReq();
        userJoinReq.setRole(Role.ROLE_ADMIN);
        userJoinReq.setPassword("admin");
        userJoinReq.setEmail("admin@naver.com");
        userJoinReq.setEmailCheck(true);
        userJoinReq.setPasswordConfirm("amdin");
        userJoinReq.setLoginId("admin");
        userJoinReq.setName("adminUser");
        userJoinReq.setNickname("닉네임");
        return userJoinReq;
    }

    protected UserJoinReq newUser(){
        UserJoinReq userJoinReq = new UserJoinReq();
        userJoinReq.setRole(Role.ROLE_USER);
        userJoinReq.setPassword("user");
        userJoinReq.setEmail("user@naver.com");
        userJoinReq.setEmailCheck(true);
        userJoinReq.setPasswordConfirm("user");
        userJoinReq.setLoginId("user");
        userJoinReq.setName("user");
        userJoinReq.setNickname("user닉네임");
        return userJoinReq;
    }

    protected User newMockUser(Long id, String username, String fullname){
        return null;

    }

}
