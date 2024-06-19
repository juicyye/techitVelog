package techit.velog.domain.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.liks.entity.Likes;
import techit.velog.domain.posttag.entity.PostTag;
import techit.velog.domain.uploadfile.UploadFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static techit.velog.domain.post.dto.PostReqDto.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Posts extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private IsReal isReal;
    @Enumerated(EnumType.STRING)
    private IsSecret isSecret;
    private int views;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "upload_file_id")
    private UploadFile uploadFile;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<UploadFile> uploadFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Likes> likes =new ArrayList<>();

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL)
    private List<PostTag> postTags = new ArrayList<>();


    /**
     * 생성자
     */
    public Posts(PostReqDtoWeb postReqDtoWeb, Blog blog) {
        this.title = postReqDtoWeb.getTitle();
        this.content = postReqDtoWeb.getContent();
        this.isSecret = postReqDtoWeb.getIsSecret();
        if (blog != null) {
            setBlog(blog);
        }

        if (postReqDtoWeb.getIsReal()) {
            this.isReal = IsReal.REAL;
        } else this.isReal = IsReal.TEMP;

    }

    /**
     * 편의 메서드
     */

    public void setBlog(Blog blog) {
        this.blog = blog;
        blog.getPosts().add(this);
    }

    /**
     * 비즈니스 메서드
     */

    public void addView(){
        this.views++;
    }

    public void changeUploadFile(List<UploadFile> uploadFiles, UploadFile uploadFile) {
        this.uploadFiles = uploadFiles;
        this.uploadFile = uploadFile;
    }

    public void changeReal() {

    }
}
