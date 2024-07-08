package techit.velog.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
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
    public static class UserRespDtoWebAdmin {
        private Long userId;
        private String loginId;
        private String email;
        private String nickname;
        private String name;
        private Role role;
        private UploadFile userImage;

        public UserRespDtoWebAdmin(User user) {
            this.userId = user.getId();
            this.loginId = user.getLoginId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.name = user.getName();
            this.role = user.getRole();
            this.userImage = user.getUploadFile();

        }
    }
}
