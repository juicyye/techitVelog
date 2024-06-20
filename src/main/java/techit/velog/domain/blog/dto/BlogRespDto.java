package techit.velog.domain.blog.dto;

import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.uploadfile.UploadFile;


import java.time.LocalDateTime;


public class BlogRespDto {
    @Getter
    @Setter
    public static class BlogRespDtoWeb{
        private Long blogId;
        private Long userId;
        private String nickname;
        private String title;
        private String description;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private UploadFile image;
        private int followers;
        private int followings;
    }
}
