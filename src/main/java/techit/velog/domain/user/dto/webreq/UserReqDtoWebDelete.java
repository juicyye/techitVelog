package techit.velog.domain.user.dto.webreq;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserReqDtoWebDelete {
    @NotEmpty
    private String password;
}
