package techit.velog.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.uploadfile.UploadFile;

import java.util.ArrayList;
import java.util.List;

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
    private Long id;
    private String userId;

    private String name;
    private String loginId;
    private String password;
    private boolean emailCheck;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Blog blog;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user",cascade = CascadeType.ALL)
    private UploadFile uploadFile;

    @OneToMany(mappedBy = "user2",cascade = CascadeType.ALL)
    private List<Blog> blogs = new ArrayList<>();
    /**
     * 회원가입 Dto -> Entity
     */
    public static User toEntity(UserJoinReq userJoinReq) {
        return User.builder()
                .userId(userJoinReq.getUserId())
                .name(userJoinReq.getName())
                .loginId(userJoinReq.getLoginId())
                .password(userJoinReq.getPassword())
                .email(userJoinReq.getEmail())
                .role(userJoinReq.getRole())
                .emailCheck(userJoinReq.isEmailCheck())
                .build();
    }

}
