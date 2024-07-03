package techit.velog.domain.comment.dto;

import lombok.*;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.comment.entity.IsDeleted;

import java.util.ArrayList;
import java.util.List;

public class CommentReqDtoWeb {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentReqDtoWebBasic {
        private Long commentId;
        private Long postId;
        private String content;
        private String loginId;
        private IsDeleted isDeleted;
        private List<CommentReqDtoWebBasic> comments = new ArrayList<>();

        public CommentReqDtoWebBasic(Long commentId, String content) {
            this.commentId = commentId;
            this.content = content;
        }
    }
    @Data
    public static class CommentReqDtoWebUpdate{
        private Long commentId;
        private String content;

        public CommentReqDtoWebUpdate(Comment comment) {
            this.commentId = comment.getId();
            this.content = comment.getContent();
        }
    }
}
