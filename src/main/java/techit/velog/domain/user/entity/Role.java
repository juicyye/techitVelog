package techit.velog.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("유저"),ROLE_ADMIN("관리자");
    private String description;

}
