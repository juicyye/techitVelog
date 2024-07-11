package techit.velog.domain.user.dto.webresp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.entity.EmailCheck;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDtoWeb {
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
    private String blogName;
    private String loginId;
    private Role role;

    public UserRespDtoWeb(User user) {
        this.name = user.getName();
        this.userId = user.getId();
        this.email = user.getEmail();
        this.loginId = user.getLoginId();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.password = user.getPassword();
        this.userImage = user.getUploadFile();
        if (user.getEmailCheck().equals(EmailCheck.ALLOW)) {
            this.emailCheck = true;
        }
    }

    public UserRespDtoWeb(User user, String blogName) {
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.emailCheck = user.getEmailCheck().equals(EmailCheck.ALLOW);
        this.userImage = user.getUploadFile();
        this.blogName = blogName;
        this.loginId = user.getLoginId();
        this.role = user.getRole();
    }
}
