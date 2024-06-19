package techit.velog.domain.post.dto;


import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.comment.dto.CommentRespDto;
import techit.velog.domain.liks.dto.LikeRespDto;
import techit.velog.domain.tag.dto.TagRespDto;
import techit.velog.domain.uploadfile.UploadFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.liks.dto.LikeRespDto.*;
import static techit.velog.domain.tag.dto.TagRespDto.*;

public class PostRespDto {
    @Getter
    @Setter
    public static class PostRespDtoWeb{
        private Long id;
        private String blogName;
        private String title;
        private String content;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private int views;
        private List<CommentRespDtoWeb> comments = new ArrayList<>();
        private List<LikeRespDtoWeb> likes = new ArrayList<>();
        private List<TagRespDtoWeb> tags = new ArrayList<>();

        private List<UploadFile> imageFiles;
        private UploadFile  uploadFile;
    }
}
