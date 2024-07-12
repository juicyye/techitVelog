package techit.velog.domain.mail.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MailEvent {
    private String email;

    public MailEvent(String email) {
        this.email = email;
    }
}
