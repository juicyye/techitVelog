package techit.velog.domain.post.dto;


import lombok.*;
import techit.velog.domain.post.entity.IsReal;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.uploadfile.UploadFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static techit.velog.domain.comment.dto.CommentRespDto.*;

public class PostRespDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostRespDtoWeb{
        private Long postId;
        private Long blogId;
        private String title;
        private String content;
        private String description;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private int views;
        private String nickname;
        private String blogName;
        private int likes;
        private String tagName;

        private List<CommentRespDtoWeb> comments = new ArrayList<>();

        private List<UploadFile> postImages;
        private UploadFile postImage;


    }

    @Getter
    @Setter
    @ToString
    public static class PostRespDtoWebAll{
        private Long postId;
        private String content;
        private String title;
        private String postDescription;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private String nickname;
        private String blogName;
        private Long likes;
        private Long comments;
        private int views;
        private UploadFile postImage;
        private UploadFile userImage;
    }

    @Getter
    @Setter
    public static class PostRespDtoWebUpdate{
        private Long postId;
        private Long blogId;
        private String title;
        private String content;
        private String description;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private IsSecret isSecret;
        private Boolean isTemp;
        private String tagName;
        private UploadFile uploadFile;
        private List<UploadFile> uploadFiles = new ArrayList<>();

        public PostRespDtoWebUpdate(Posts posts) {
            this.postId = posts.getId();
            this.blogId = posts.getBlog().getId();
            this.title = posts.getTitle();
            this.content = posts.getContent();
            this.description = posts.getDescription();
            this.createDate = posts.getCreateDate();
            this.updateDate = posts.getUpdateDate();
            this.isSecret = posts.getIsSecret();
            this.uploadFile = posts.getUploadFile();
            this.uploadFiles = posts.getUploadFiles();
            this.isTemp = posts.getIsReal().equals(IsReal.TEMP);
        }
    }
    @Getter
    @Setter
    public static class PostRespDtoWebTag{
        private Long postId;
        private Long blogId;
        private String title;
        private String content;
        private String description;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private int views;
        private String nickname;
        private String blogName;
        private String tagName;
        private Long likes;
        private Long comments;
        private Long tagCount;


    }


}
