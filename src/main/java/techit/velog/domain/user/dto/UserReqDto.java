package techit.velog.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserReqDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserReqDtoWebUpdate {
        private Long userId;
        @UserUpdate
        @NotEmpty

        private String name;
        @UserUpdate
        @Email
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
        @Pattern(regexp = "^[a-zA-Z가-힣]{2,10}$",message = "블로그 이름은 영어, 한글 2~10 이내로 적어주세요")
        private String name;
        @JoinUnique
        @Pattern(regexp = "^[a-zA-Z]{2,20}$",message = "아이디은 영문 2~20 이내로 적어주세요")
        private String loginId;
        @NotEmpty
        @Pattern(
                regexp = "^(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{6,20}$",
                message = "비밀번호는 6자 이상 20자 이하이어야 하며, 공백을 포함할 수 없고, 특수문자를 두 개 이상 포함해야 합니다."
        )
        private String password;
        private String passwordConfirm;
        private boolean emailCheck;
        @JoinUnique
        private String email;
        @JoinUnique
        private String nickname;
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLoginReq{
        @NotEmpty
        private String loginId;
        @NotEmpty
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
        @NotEmpty
        private String password;
    }

    @Data
    @Builder
    public static class UserReqDtoWebAdmin {
        private Long userId;
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z]{2,20}$",message = "아이디은 영문 2~20 이내로 적어주세요")
        private String loginId;
        @Email
        @NotEmpty
        private String email;

        private String nickname;
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z가-힣]{2,10}$",message = "블로그 이름은 영어, 한글 2~10 이내로 적어주세요")
        private String name;
        private Role role;
        private MultipartFile userImage;

    }

}
