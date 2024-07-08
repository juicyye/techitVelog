package techit.velog.domain.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.follow.entity.Follow;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.tag.entity.Tags;
import techit.velog.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Blog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;
    private String title; // 블로그 제목
    private String description; // 블로그 설명
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "blog",cascade = CascadeType.ALL)
    private List<Tags> tags = new ArrayList<>();

    @OneToMany(mappedBy = "blog",cascade = CascadeType.ALL)
    private List<Posts> posts = new ArrayList<>();


    public Blog(String title, User user) {
        this.title = title;
        this.description = "내가 만든 쿠키";
        if (user != null) {
            addUser(user);
        }
    }

    public void addUser(User user) {
        this.user = user;
        user.setBlog(this);
    }

    public void changeInfo(String description,String title) {
        this.description = description;
        this.title = title;
    }

    public void changeTitle(String title) {
        this.title = title;
    }
}
