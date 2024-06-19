package techit.velog.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.entity.User;

public class UserRespDto {
    @Getter
    @Setter
    public static class UserRespDtoWeb{
        private Long id;
        private String userId;
        private String name;
        private String nickname;
        private String email;
        private boolean emailCheck;

        private UploadFile image;

        public UserRespDtoWeb(User user) {
            this.id = user.getId();
            this.userId = user.getUserId();
            this.name = user.getName();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.image = user.getUploadFile();
        }
    }
}
