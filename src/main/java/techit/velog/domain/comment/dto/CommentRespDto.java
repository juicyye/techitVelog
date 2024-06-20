package techit.velog.domain.comment.dto;

import lombok.*;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.comment.entity.IsDeleted;

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
        private Long postId;
        private Long parentId;
        private String content;
        private String loginId;
        private IsDeleted deleteStatus;
        private List<CommentRespDtoWeb> child = new ArrayList<>();

        public static CommentRespDtoWeb toDto(Comment comment) {
            Long parentId = comment.getParent() != null ? comment.getParent().getId() : null;
            return CommentRespDtoWeb.builder()
                    .commentId(comment.getId())
                    .postId(comment.getPosts().getId())
                    .parentId(parentId)
                    .content(comment.getContent())
                    .deleteStatus(comment.getIsDeleted())
                    .build();
        }


    }
}
