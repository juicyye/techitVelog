package techit.velog.domain.comment.dto;

import lombok.*;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.comment.entity.IsDeleted;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentRespDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentRespDtoWeb{
        private Long commentId;
        private Long parentId;
        private String blogName;
        private String postTitle;
        private String content;
        private IsDeleted deleteStatus;
        @Builder.Default
        private List<CommentRespDtoWeb> child = new ArrayList<>();

        public static CommentRespDtoWeb toDto(Comment comment) {

            return CommentRespDtoWeb.builder()
                    .commentId(comment.getId())
                    .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                    .content(comment.getContent())
                    .deleteStatus(comment.getIsDeleted())
                    .build();
        }
    }
    @Data
    public static class commentRespDtoWebUpdate {
        private Long commentId;
        private String content;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;

        public commentRespDtoWebUpdate(Comment comment) {
            this.commentId = comment.getId();
            this.content = comment.getContent();
            this.createDate = comment.getCreateDate();
            this.updateDate = comment.getUpdateDate();
        }
    }
}
