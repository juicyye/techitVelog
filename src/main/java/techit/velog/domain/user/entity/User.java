package techit.velog.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.dto.UserRespDtoWeb;
import techit.velog.global.dto.OAuth2Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static techit.velog.domain.user.dto.UserReqDto.*;
import static techit.velog.domain.user.dto.UserRespDtoWeb.*;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Blog blog;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "upload_file_id")
    private UploadFile uploadFile;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    /**
     * 회원가입 Dto -> Entity
     */
    public static User toEntity(UserJoinReq userJoinReq, UploadFile uploadFile) {
        EmailCheck emailCheck1 = userJoinReq.isEmailCheck() ? EmailCheck.ALLOW : EmailCheck.DENY;
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .name(userJoinReq.getName())
                .nickname(userJoinReq.getNickname())
                .loginId(userJoinReq.getLoginId())
                .password(userJoinReq.getPassword())
                .email(userJoinReq.getEmail())
                .emailCheck(emailCheck1)
                .uploadFile(uploadFile)
                .role(Role.ROLE_USER)
                .build();
    }

    /**
     * 관리자 전용
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
                .emailCheck(emailCheck1)
                .role(Role.ROLE_ADMIN)
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

    public User(String userId, String name, String nickname, String loginId, String password, String email) {
        this.name = name;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
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

    public boolean isUserEmail(String email) {
        return this.email.equals(email);
    }

    public boolean isUserNickname(String nickname) {
        return this.nickname.equals(nickname);
    }
    public boolean isUserName(String name) {
        return this.name.equals(name);
    }

    public void changeInfoAdmin(UserReqDtoWebAdmin userReqDtoWebAdmin, UploadFile uploadFile) {
        this.email = userReqDtoWebAdmin.getEmail();
        this.name = userReqDtoWebAdmin.getName();
        this.nickname = userReqDtoWebAdmin.getNickname();
        this.loginId = userReqDtoWebAdmin.getLoginId();
        this.role = userReqDtoWebAdmin.getRole();
        this.uploadFile = uploadFile;
    }
}
