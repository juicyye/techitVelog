package techit.velog.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsTemp {
    TEMP("임시저장"), SAVE("저장");
    String description;
}
