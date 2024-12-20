package techit.velog.domain.liks.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.user.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Likes extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;

    public Likes(User user, Posts posts) {
        this.user = user;
        addPosts(posts);

    }

    /**
     * 편의 메소드
     */
    public void addPosts(Posts posts) {
        this.posts = posts;
        posts.getLikes().add(this);
    }
}
