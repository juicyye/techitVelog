package techit.velog.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techit.velog.domain.comment.entity.IsDeleted;

import java.util.ArrayList;
import java.util.List;

public class CommentReqDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentReqDtoWeb{
        private Long commentId;
        private Long postId;
        private String content;
        private String loginId;
        private IsDeleted isDeleted;
        private List<CommentReqDtoWeb> comments = new ArrayList<>();

        public CommentReqDtoWeb(Long commentId, String content) {
            this.commentId = commentId;
            this.content = content;
        }
    }
}
