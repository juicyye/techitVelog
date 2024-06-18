package techit.velog.domain.posttag.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.tag.entity.Tags;

import javax.swing.text.html.HTML;

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
    public PostTag(Tags tags) {
        PostTag postTag = new PostTag();
        postTag.setTags(tags);
    }

    /**
     * 편의 메서드
     */

    private void setTags(Tags tags) {
        this.tags = tags;
        tags.getPostTags().add(this);
    }


}
