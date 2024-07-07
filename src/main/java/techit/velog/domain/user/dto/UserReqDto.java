package techit.velog.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;
import techit.velog.global.valid.JoinUnique;
import techit.velog.global.valid.UserUpdate;

@Slf4j
public class UserReqDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserReqDtoWebUpdate {
        private Long userId;
        @UserUpdate
        private String name;
        @UserUpdate
        private String email;
        @UserUpdate
        private String nickname;
        private String password;
        private String changePassword;
        private String changePasswordConfirm;
        private boolean emailCheck;
        private MultipartFile userImage;
        private String blogDescription;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserJoinReq{
        @JoinUnique
        private String name;
        @JoinUnique
        private String loginId;
        private String password;
        private String passwordConfirm;
        private boolean emailCheck;
        @JoinUnique
        private String email;
        private Role role;
        @JoinUnique
        private String nickname;
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
        private String name;
        private String email;
        private String loginId;
        private String password;
        private Role role;

        public AccountDto(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.loginId = user.getLoginId();
            this.role = user.getRole();
        }

        public AccountDto(Long id, String name, String loginId, Role role) {
            this.id = id;
            this.name = name;
            this.loginId = loginId;
            this.role = role;
        }

        public AccountDto(String loginId, String role) {
            this.loginId = loginId;
            this.role = Role.valueOf(role);
        }

        public static AccountDto toDto(User user){
            return AccountDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .loginId(user.getLoginId())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .email(user.getEmail())
                    .build();
        }

    }

    @Data
    public static class UserReqDeleteDto{
        private String password;
    }

}
