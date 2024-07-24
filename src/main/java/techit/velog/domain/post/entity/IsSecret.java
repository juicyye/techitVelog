package techit.velog.domain.post.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IsSecret {
    SECRET("비밀글"), NORMAL("일반글");
    String description;
}
