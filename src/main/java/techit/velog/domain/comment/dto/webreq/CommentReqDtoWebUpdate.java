package techit.velog.domain.comment.dto.webreq;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReqDtoWebUpdate {
    private Long commentId;
    @NotEmpty
    @Size(min = 2, message = "2글자 이상 적어주세요")
    private String content;
}
