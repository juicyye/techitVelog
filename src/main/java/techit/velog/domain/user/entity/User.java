package techit.velog.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.uploadfile.UploadFile;

import static techit.velog.domain.user.dto.UserReqDto.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String loginId;
    private String password;
    private boolean emailCheck;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    private UploadFile uploadFile;

    /**
     * Dto -> Entity
     */
    public static User toEntity(UserJoinReq userJoinReq) {
        return User.builder()
                .name(userJoinReq.getName())
                .loginId(userJoinReq.getLoginId())
                .password(userJoinReq.getPassword())
                .email(userJoinReq.getEmail())
                .role(userJoinReq.getRole())
                .emailCheck(userJoinReq.isEmailCheck())
                .build();
    }

}
