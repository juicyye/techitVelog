package techit.velog.domain.user.dto.webreq;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.user.entity.Role;

@Data
public class UserReqDtoWebAdminUpdate {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]{2,20}$",message = "아이디은 영문 2~20 이내로 적어주세요")
    private String loginId;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,10}$",message = "블로그 이름은 영어, 한글 2~10 이내로 적어주세요")
    private String nickname;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣]{2,10}$",message = "블로그 이름은 영어, 한글 2~10 이내로 적어주세요")
    private String name;
    private Role role;
    private MultipartFile userImage;
}
