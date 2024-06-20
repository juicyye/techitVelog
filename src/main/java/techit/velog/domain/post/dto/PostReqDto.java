package techit.velog.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.post.entity.IsSecret;

import java.util.List;

public class PostReqDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PostReqDtoWeb {
        private String title;
        private String content;
        private String description;
        private Boolean isReal;
        private IsSecret isSecret;
        private String tagName;
        private List<MultipartFile> imageFiles;
        private MultipartFile uploadFile;

        public PostReqDtoWeb(String title, String content) {
            this.title = title;
            this.content = content;
            isReal = true;
            isSecret = IsSecret.NORMAL;
        }
    }
}
