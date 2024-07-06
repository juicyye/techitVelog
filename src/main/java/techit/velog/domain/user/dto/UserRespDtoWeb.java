package techit.velog.domain.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.entity.EmailCheck;
import techit.velog.domain.user.entity.User;

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
    public static class UserRespWebInfo {
        private Long userId;
        private String blogName;
        private String email;
        private String nickname;
        private String name;
        private boolean emailCheck;
        private UploadFile userImage;

        public UserRespWebInfo(User user, String blogName) {
            this.userId = user.getId();
            this.blogName = blogName;
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.name = user.getName();
            this.emailCheck = user.getEmailCheck().equals(EmailCheck.ALLOW);
            this.userImage = user.getUploadFile();
        }
    }
}
