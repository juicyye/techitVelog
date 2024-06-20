package techit.velog.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.comment.dto.CommentReqDto;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.user.entity.User;

import java.util.LinkedList;
import java.util.List;

import static techit.velog.domain.comment.dto.CommentReqDto.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;
    @Enumerated(EnumType.STRING)
    private IsDeleted isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> child = new LinkedList<>();

    /**
     * 생성자
     */

    public Comment(CommentReqDtoWeb commentReqDtoWeb, Posts posts, User user) {
        this.content = commentReqDtoWeb.getContent();
        this.isDeleted = IsDeleted.NORMAL;
        this.user= user;
        addPost(posts);

    }

    /**
     * 편의 메서드
     */
    public void addPost(Posts posts) {
        this.posts = posts;
        posts.getComments().add(this);
    }

    public void addChild(Comment child) {
        this.getChild().add(child);
        child.parent = this;
    }
}
