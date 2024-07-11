package techit.velog.domain.post.dto.webresp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import techit.velog.domain.post.entity.IsReal;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.uploadfile.UploadFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRespDtoWeb {
    private Long postId;
    private Long blogId;
    private String loginId;
    private String title;
    private String content;
    private String postDescription;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String nickname;
    private String blogName;
    private IsSecret isSecret;
    private Long likes;
    private Long comments;
    private int views;
    private UploadFile postImage;
    private UploadFile userImage;
    private List<UploadFile> postImages = new ArrayList<>();
    private Boolean isTemp;
    private String tagName;
    private List<String> tagNames = new ArrayList<>();
    private IsReal isReal;


    public PostRespDtoWeb(Posts posts) {
        this.postId = posts.getId();
        this.blogId = posts.getBlog().getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.postDescription = posts.getDescription();
        this.createDate = posts.getCreateDate();
        this.updateDate = posts.getUpdateDate();
        this.isSecret = posts.getIsSecret();
        this.postImage = posts.getUploadFile();
        this.postImages = posts.getUploadFiles();
        this.isTemp = posts.getIsReal().equals(IsReal.TEMP);

    }
}
