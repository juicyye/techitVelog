package techit.velog.domain.post.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IsReal {
    REAL("출간"),TEMP("임시저장");
    String description;
}
