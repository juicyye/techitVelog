package techit.velog.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.entity.EmailCheck;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserRespDtoWeb {
    @Getter
    @Setter
    public static class UserRespDtoWebUpdate {
        private Long userId;
        private String name;
        private String email;
        private String nickname;
        private String password;
        private boolean emailCheck;
        private String blogDescription;
        private UploadFile userImage;
        private String changePassword;
        private String changePasswordConfirm;

        public UserRespDtoWebUpdate(User user) {
            this.name = user.getName();
            this.userId = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.password = user.getPassword();
            this.userImage = user.getUploadFile();
            if (user.getEmailCheck().equals(EmailCheck.ALLOW)) {
                this.emailCheck = true;
            }
        }
    }

    @Data
    public static class UserRespDtoWebInfo {
        private Long userId;
        private String blogName;
        private String email;
        private String nickname;
        private String name;
        private boolean emailCheck;
        private UploadFile userImage;

        public UserRespDtoWebInfo(User user, String blogName) {
            this.userId = user.getId();
            this.blogName = blogName;
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.name = user.getName();
            this.emailCheck = user.getEmailCheck().equals(EmailCheck.ALLOW);
            this.userImage = user.getUploadFile();
        }
    }

    @Data
    @Builder
    public static class UserRespDtoWebAdmin {
        private Long userId;
        private String loginId;
        private String email;
        private String nickname;
        private String name;
        private Role role;
        public static List<UserRespDtoWebAdmin> toDto(List<User> users) {
            ArrayList<UserRespDtoWebAdmin> userList = new ArrayList<>();
            for (User user : users) {
                userList.add(toDto(user));
            }
            return userList;
        }

        public static UserRespDtoWebAdmin toDto(User user) {
            return UserRespDtoWebAdmin.builder()
                    .userId(user.getId())
                    .email(user.getEmail())
                    .loginId(user.getLoginId())
                    .nickname(user.getNickname())
                    .name(user.getName())
                    .role(user.getRole())
                    .build();
        }
    }
}
