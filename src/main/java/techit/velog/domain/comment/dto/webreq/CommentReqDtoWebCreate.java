package techit.velog.domain.comment.dto.webreq;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import techit.velog.domain.comment.entity.IsDeleted;

import java.util.ArrayList;
import java.util.List;
@Data
public class CommentReqDtoWebCreate {
    private Long commentId;
    private Long postId;
    @NotEmpty
    @Size(min = 2, message = "2글자 이상 적어주세요")
    private String content;
    private String loginId;
    private IsDeleted isDeleted;
    private List<CommentReqDtoWebCreate> comments = new ArrayList<>();

    public CommentReqDtoWebCreate(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
