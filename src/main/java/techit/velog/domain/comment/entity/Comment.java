package techit.velog.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.user.entity.User;

import java.util.LinkedList;
import java.util.List;

import static techit.velog.domain.comment.dto.CommentReqDtoWeb.*;

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
     * 테스트
     */
    public Comment(String content,Posts posts) {
        this.content = content;
        addPost(posts);
    }

    public Comment(String content, Comment parent,Posts posts) {
        this.content = content;
        parent.addChild(this);
        this.parent = parent;
        addPost(posts);
    }


    /**
     * 생성자
     */

    public Comment(CommentReqDtoWebBasic commentReqDtoWebBasic, Posts posts, User user) {
        this.content = commentReqDtoWebBasic.getContent();
        this.isDeleted = IsDeleted.NORMAL;
        setUser(user);
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

    private void setUser(User user) {
        this.user = user;
        user.getComments().add(this);
    }

    /**
     * 비지니스 메서드
     */
    public void changeContent(CommentReqDtoWebUpdate commentReqDtoWebUpdate) {
        this.content = commentReqDtoWebUpdate.getContent();
    }

    public void deleteComment() {
        this.isDeleted = IsDeleted.DELETE;
        this.content = IsDeleted.DELETE.getDescription();
    }
}
