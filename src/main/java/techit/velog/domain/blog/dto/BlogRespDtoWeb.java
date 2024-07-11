package techit.velog.domain.blog.dto;

import lombok.*;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.uploadfile.UploadFile;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogRespDtoWeb {
    private Long blogId;
    private Long userId;
    private String loginId;
    private String blogName;
    private String nickname;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private UploadFile userImage;
    private int followers;
    private int followings;

    public BlogRespDtoWeb(Blog blog) {
        this.blogId = blog.getId();
        this.blogName = blog.getTitle();
        this.createDate = blog.getCreateDate();
        this.updateDate = blog.getUpdateDate();
        this.description = blog.getDescription();
    }
}
