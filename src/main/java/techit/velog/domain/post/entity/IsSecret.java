package techit.velog.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IsSecret {
    SECRET("비밀방"), NORMAL("일반방");
    String description;
}
