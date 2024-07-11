package techit.velog.domain.post.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.BaseEntity;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.liks.entity.Likes;
import techit.velog.domain.post.dto.webreq.PostReqDtoWebCreate;
import techit.velog.domain.post.dto.webreq.PostReqDtoWebUpdate;
import techit.velog.domain.posttag.entity.PostTag;
import techit.velog.domain.uploadfile.UploadFile;

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
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String description;

    @Enumerated(EnumType.STRING)
    private IsReal isReal;
    @Enumerated(EnumType.STRING)
    private IsSecret isSecret;
    private int views;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "upload_file_id")
    private UploadFile uploadFile;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UploadFile> uploadFiles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Likes> likes =new ArrayList<>();

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    /**
     * 테스트용
     */
    public Posts(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * 생성자
     */
    public Posts(PostReqDtoWebCreate postReqDtoWebCreate, Blog blog) {
        this.title = postReqDtoWebCreate.getTitle();
        this.content = postReqDtoWebCreate.getContent();
        this.isSecret = postReqDtoWebCreate.getIsSecret();
        this.description = postReqDtoWebCreate.getPostDescription();
        if (blog != null) {
            setBlog(blog);
        }
        if (postReqDtoWebCreate.getIsTemp()) {
            this.isReal = IsReal.TEMP;
        } else this.isReal = IsReal.REAL;
    }

    public Posts (Blog blog, PostReqDtoWebCreate postReqDtoWebCreate, List<PostTag> postTags) {
        this.title = postReqDtoWebCreate.getTitle();
        this.content = postReqDtoWebCreate.getContent();
        this.isSecret = postReqDtoWebCreate.getIsSecret();
        this.description = postReqDtoWebCreate.getPostDescription();
        if (postReqDtoWebCreate.getIsTemp()) {
            this.isReal = IsReal.TEMP;
        } else{
            this.isReal = IsReal.REAL;
        }
        setBlog(blog);
        for (PostTag postTag : postTags) {
            addPostTag(postTag);
        }
    }

    /**
     * 편의 메서드
     */

    public void setBlog(Blog blog) {
        this.blog = blog;
        blog.getPosts().add(this);
    }

    public void addPostTag(PostTag postTag) {
        postTags.add(postTag);
        postTag.setPost(this);
    }

    public void addImages(UploadFile uploadFile,List<UploadFile> uploadFiles) {
        this.uploadFile = uploadFile;
        this.uploadFiles.addAll(uploadFiles);
        for (UploadFile file : uploadFiles) {
            file.changePost(this);
        }
    }

    /**
     * 비즈니스 메서드
     */

    public void addView(int views){
        this.views = views;
    }

    public void change(PostReqDtoWebUpdate postReqDtoWebUpdate, UploadFile uploadFile, List<UploadFile> uploadFiles) {
        this.title = postReqDtoWebUpdate.getTitle();
        this.content = postReqDtoWebUpdate.getContent();
        this.isSecret = postReqDtoWebUpdate.getIsSecret();
        this.description = postReqDtoWebUpdate.getPostDescription();
        if (postReqDtoWebUpdate.getIsTemp()) {
            this.isReal = IsReal.TEMP;
        } else this.isReal = IsReal.REAL;
        removeUpload();
        addImages(uploadFile,uploadFiles);
    }

    public void removePostTag(){
        this.postTags.clear();
    }

    /**
     * 이미지 파일 삭제
     */
    public void removeUpload(){
        this.uploadFiles.clear();
        this.uploadFile = null;
    }
}
