package techit.velog.domain.user.dto.webreq;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import techit.velog.global.valid.JoinUnique;
@Data
public class UserReqDtoWebJoin {
    @JoinUnique
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{2,10}$",message = "블로그 이름은 영문, 한글 2~10 이내로 적어주세요")
    private String name;
    @JoinUnique
    @Pattern(regexp = "^[a-zA-Z가-힣ㄱ-ㅎ0-9]{2,20}$",message = "아이디은 영문 2~20 이내로 적어주세요")
    private String loginId;
    @NotEmpty
    @Pattern(
            regexp = "(?=.*[!@#$%]).{8,20}",
            message = "8~20자 사이의 비밀번호는 한글, 영어, 숫자, 특수문자를 포함해야합니다. (!@#$%)"
    )
    private String password;
    private String passwordConfirm;
    private boolean emailCheck;
    @JoinUnique
    @Email
    private String email;
    @JoinUnique
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,10}", message = "영문,한글,숫자 2~10 이내로 적어주세요")
    private String nickname;
}
