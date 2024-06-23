package techit.velog.domain.post.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.uploadfile.UploadFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostReqDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PostReqDtoWeb {
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9-가-힣]{2,20}$")
        private String title;
        private String content;
        private String description;
        private Boolean isTemp;
        private IsSecret isSecret;
        private String tagName;
        private List<MultipartFile> imageFiles;
        private MultipartFile uploadFile;

        /**
         * 테스트용
         */
        public PostReqDtoWeb(String title, String content) {
            this.title = title;
            this.content = content;
            isTemp = true;
            isSecret = IsSecret.NORMAL;
        }
    }
    @Getter
    @Setter
    public static class PostReqDtoWebUpdate{
        private Long postId;
        private Long blogId;
        private String title;
        private String content;
        private String description;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private IsSecret isSecret;
        private Boolean isTemp;
        private String tagName;
        private UploadFile uploadFile;
        private List<UploadFile> uploadFiles = new ArrayList<>();
    }
}
