package techit.velog.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;

public class UserReqDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserJoinReq{
        private String userId;
        private String name;
        private String loginId;
        private String password;
        private String passwordConfirm;
        private boolean emailCheck;
        private String email;
        private Role role;

    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLoginReq{
        private String loginId;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AccountDto{
        private Long id;
        private String loginId;
        private String password;
        private Role role;

        public static AccountDto toDto(User user){
            return AccountDto.builder()
                    .id(user.getId())
                    .loginId(user.getLoginId())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .build();
        }

    }
}
