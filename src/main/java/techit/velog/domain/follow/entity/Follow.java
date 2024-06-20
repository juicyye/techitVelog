package techit.velog.domain.follow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.blog.entity.Blog;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_blogId")
    private Blog from_blog;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_blogId")
    private Blog to_blog;

    /**
     * 회원 A -> 회원 B를 팔로우한다
     * from blog가 to blog를 팔로우한다
     */

    public Follow(Blog from_blog, Blog to_blog) {
        addFrom_blog(from_blog);
        addTo_blog(to_blog);
    }

    /**
     * 편의 메서드
     */


    public void addFrom_blog(Blog from_blog) {
        this.from_blog = from_blog;
    }


    public void addTo_blog(Blog to_blog) {
        this.to_blog = to_blog;

    }

}
