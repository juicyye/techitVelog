package techit.velog.domain.user.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EmailCheck {
    ALLOW("수신허용"), DENY("수신거부");
    String dscription;
}
