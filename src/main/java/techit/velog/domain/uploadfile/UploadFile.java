package techit.velog.domain.uploadfile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.user.entity.User;

@Getter
@Entity
@NoArgsConstructor
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uploadFileName;
    private String storeFileName;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "uploadFile")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploadFiles")
    private Posts posts;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;

    }

    public UploadFile(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    /**
     * 편의 메서드
     */
    public void changePost(Posts post) {
        this.posts = post;
    }
}
