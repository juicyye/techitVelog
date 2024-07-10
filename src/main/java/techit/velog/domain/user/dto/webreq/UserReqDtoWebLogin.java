package techit.velog.domain.user.dto.webreq;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserReqDtoWebLogin {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
