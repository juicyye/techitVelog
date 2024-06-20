package techit.velog.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.comment.entity.IsDeleted;

import java.util.ArrayList;
import java.util.List;

public class CommentReqDto {
    @Getter
    @Setter
    public static class CommentReqDtoWeb{
        private Long commentId;
        private Long postId;
        private String content;
        private String loginId;
        private IsDeleted isDeleted;
        private List<CommentReqDtoWeb> comments = new ArrayList<>();

    }
}
