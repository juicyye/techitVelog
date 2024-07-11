package techit.velog.domain.comment.dto.webresp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.comment.entity.IsDeleted;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRespDtoWeb {
    private Long commentId;
    private Long parentId;
    private String nickname;
    private String content;
    private IsDeleted deleteStatus;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    @Builder.Default
    private List<CommentRespDtoWeb> child = new ArrayList<>();

    public static CommentRespDtoWeb toDto(Comment comment) {
        return CommentRespDtoWeb.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .content(comment.getContent())
                .deleteStatus(comment.getIsDeleted())
                .nickname(comment.getUser().getNickname())
                .build();
    }

    public CommentRespDtoWeb(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createDate = comment.getCreateDate();
        this.updateDate = comment.getUpdateDate();
    }
}
