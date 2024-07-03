package techit.velog.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;
@Slf4j
public class UserReqDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserReqDtoWebUpdate {
        private Long userId;
        private String email;
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
        private String name;
        private String loginId;
        private String password;
        private String passwordConfirm;
        private boolean emailCheck;
        private String email;
        private Role role;
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

    @Data
    public static class UserReqDeleteDto{
        private String password;
    }
}
