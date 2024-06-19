package techit.velog.domain.posttag.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.tag.entity.Tags;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class PostTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tags tags;


    /**
     * 생성메서드
     */
    public static void ChangeTag(Tags tags, Posts posts) {
        PostTag postTag = new PostTag();
        postTag.setTags(tags);
        postTag.changePost(posts);
    }

    /**
     * 편의 메서드
     */

    private void setTags(Tags tags) {
        this.tags = tags;
        tags.getPostTags().add(this);
    }


    public void changePost(Posts posts) {
        this.posts = posts;
        posts.getPostTags().add(this);
    }
}
