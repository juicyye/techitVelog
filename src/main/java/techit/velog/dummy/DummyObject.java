package techit.velog.dummy;


import techit.velog.domain.user.dto.webreq.UserReqDtoWebJoin;
import techit.velog.domain.user.entity.User;



public class DummyObject {
    protected UserReqDtoWebJoin adminUser(){
        UserReqDtoWebJoin userJoinReq = new UserReqDtoWebJoin();
        userJoinReq.setPassword("admin");
        userJoinReq.setEmail("admin@naver.com");
        userJoinReq.setEmailCheck(true);
        userJoinReq.setPasswordConfirm("amdin");
        userJoinReq.setLoginId("admin");
        userJoinReq.setName("adminUser");
        userJoinReq.setNickname("닉네임");
        return userJoinReq;
    }

    protected UserReqDtoWebJoin newUser(){
        UserReqDtoWebJoin userJoinReq = new UserReqDtoWebJoin();
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
