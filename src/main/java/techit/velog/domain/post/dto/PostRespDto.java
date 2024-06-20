package techit.velog.domain.post.dto;


import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.uploadfile.UploadFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.liks.dto.LikeRespDto.*;

public class PostRespDto {
    @Getter
    @Setter
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


}
