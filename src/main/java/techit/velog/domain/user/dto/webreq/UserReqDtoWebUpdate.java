package techit.velog.domain.user.dto.webreq;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.global.valid.UserUpdate;

@Data
public class UserReqDtoWebUpdate {
    private Long userId;
    @UserUpdate
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{2,10}$",message = "블로그 이름은 영문, 한글 2~10 이내로 적어주세요")
    private String name;
    @UserUpdate
    @Email
    private String email;
    @UserUpdate
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,10}", message = "영문,한글,숫자 2~10 이내로 적어주세요")
    private String nickname;
    private String password;
    @NotEmpty
    @Pattern(
            regexp = "(?=.*[!@#$%]).{8,20}",
            message = "8~20자 사이의 비밀번호는 한글, 영어, 숫자, 특수문자를 포함해야합니다. (!@#$%)"
    )
    private String changePassword;
    private String changePasswordConfirm;
    private boolean emailCheck;
    private MultipartFile userImage;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[ㄱ-ㅎ가-힣a-zA-Z0-9])[ㄱ-ㅎ가-힣a-zA-Z0-9\\s]{1,8}[ㄱ-ㅎ가-힣a-zA-Z0-9]$", message = "2~20자 이내로 적어주세요")
    private String blogDescription;
}
