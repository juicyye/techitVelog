package techit.velog.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import techit.velog.domain.uploadfile.UploadFile;

public class FollowRespDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FollowRespDtoWeb {
        private Long followId;
        private String nickname;
        private String description;
        private String blogName;
        private UploadFile userImage;

    }
}
