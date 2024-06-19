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
        private String blogName;
        private String title;
        private String content;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private int views;
        private List<CommentRespDtoWeb> comments = new ArrayList<>();
        private List<LikeRespDtoWeb> likes = new ArrayList<>();

        private UploadFile UserImage;
        private List<UploadFile> postImages;
        private UploadFile postImage;
    }
}
