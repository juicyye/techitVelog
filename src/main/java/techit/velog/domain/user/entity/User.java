package techit.velog.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.global.dto.OAuth2Response;

import java.util.UUID;

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
    private String nickname;
    private String loginId;
    private String password;
    @Enumerated(EnumType.STRING)
    private EmailCheck emailCheck;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "upload_file_id")
    private UploadFile uploadFile;
    /**
     * 회원가입 Dto -> Entity
     */
    public static User toEntity(UserJoinReq userJoinReq) {
        EmailCheck emailCheck1 = userJoinReq.isEmailCheck() ? EmailCheck.ALLOW : EmailCheck.DENY;
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .name(userJoinReq.getName())
                .nickname(userJoinReq.getNickname())
                .loginId(userJoinReq.getLoginId())
                .password(userJoinReq.getPassword())
                .email(userJoinReq.getEmail())
                .role(userJoinReq.getRole())
                .emailCheck(emailCheck1)
                .build();
    }

    /**
     * OAuth2 전용 생성자
     */
    public User(OAuth2Response oAuth2Response) {
        this.userId = UUID.randomUUID().toString();
        this.nickname = oAuth2Response.getName();
        this.loginId = oAuth2Response.getLoginId();
        this.name = oAuth2Response.getName();
        this.email = oAuth2Response.getEmail();
        this.emailCheck = EmailCheck.ALLOW;
        this.role = Role.ROLE_USER;
    }

    /**
     * 편의 메서드
     */
    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    /**
     * 비지니스 메서드
     */

    public void changeInfo(UserReqDtoWebUpdate userReqDtoWeb, UploadFile uploadFile) {
        EmailCheck emailCheck1 = userReqDtoWeb.isEmailCheck() ? EmailCheck.ALLOW : EmailCheck.DENY;
        this.nickname = userReqDtoWeb.getNickname();
        this.email = userReqDtoWeb.getEmail();
        this.password = userReqDtoWeb.getChangePassword();
        this.emailCheck = emailCheck1;
        this.uploadFile = uploadFile;
        this.name = userReqDtoWeb.getName();

    }

    public void changeOauth(OAuth2Response oAuth2Response) {
        this.email = oAuth2Response.getEmail();
        this.name = oAuth2Response.getName();
        this.email = oAuth2Response.getLoginId();
    }
}
