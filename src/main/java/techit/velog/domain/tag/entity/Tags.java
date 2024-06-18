package techit.velog.domain.tag.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.posttag.entity.PostTag;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Tags extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tags",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public Tags(String name,Blog blog) {
        this.name = name;
        if(blog != null) {
            setBlog(blog);
        }
    }

    /**
     * 편의 메서드
     */
    public void setBlog(Blog blog) {
        this.blog = blog;
        blog.getTags().add(this);
    }
}
