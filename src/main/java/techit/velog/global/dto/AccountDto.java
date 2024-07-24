package techit.velog.global.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import techit.velog.domain.user.entity.Role;
import techit.velog.domain.user.entity.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String name;
    private String email;
    private String loginId;
    private String password;
    private Role role;

    public AccountDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.loginId = user.getLoginId();
        this.role = user.getRole();
    }

    public AccountDto(Long id, String name, String loginId, Role role) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.role = role;
    }

    public AccountDto(String loginId, String role, String name) {
        this.loginId = loginId;
        this.role = Role.valueOf(role);
        this.name = name;

    }

    public static AccountDto toDto(User user){
        return AccountDto.builder()
                .id(user.getId())
                .name(user.getName())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }
}
