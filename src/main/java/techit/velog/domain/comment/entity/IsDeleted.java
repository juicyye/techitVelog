package techit.velog.domain.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsDeleted {
    DELETE("삭제된 댓글입니다."), NORMAL("1");
    private final String description;
}
