package techit.velog.domain.comment.dto.webreq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReqDtoWebUpdate {
    private Long commentId;
    private String content;
}
