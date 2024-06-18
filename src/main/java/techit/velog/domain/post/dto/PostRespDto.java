package techit.velog.domain.post.dto;


import techit.velog.domain.comment.dto.CommentRespDto;
import techit.velog.domain.liks.dto.LikeRespDto;
import techit.velog.domain.tag.dto.TagRespDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.liks.dto.LikeRespDto.*;
import static techit.velog.domain.tag.dto.TagRespDto.*;

public class PostRespDto {

    public static class PostRespDtoWeb{
        private Long id;
        public String userId;
        private String postId;
        private String title;
        private String content;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private List<CommentRespDtoWeb> comments = new ArrayList<>();
        private List<LikeRespDtoWeb> likes = new ArrayList<>();
        private List<TagRespDtoWeb> tags = new ArrayList<>();

    }
}
