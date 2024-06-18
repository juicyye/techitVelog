package techit.velog.domain.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.comment.Comment;
import techit.velog.domain.liks.entity.Likes;
import techit.velog.domain.posttag.PostTag;
import techit.velog.domain.tag.Tags;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Posts extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String postId;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private IsTemp isTemp;
    @Enumerated(EnumType.STRING)
    private IsSecret isSecret;
    private int views;

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UploadFile> uploadFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Likes> likes =new ArrayList<>();

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

}
