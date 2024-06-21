package techit.velog.domain.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.uploadfile.UploadFile;


import java.time.LocalDateTime;


public class BlogRespDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
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

        public BlogRespDtoWeb(Blog blog) {
            this.blogId = blog.getId();
            this.title = blog.getTitle();
            this.createDate = blog.getCreateDate();
            this.updateDate = blog.getUpdateDate();
            this.description = blog.getDescription();
        }
    }
}
