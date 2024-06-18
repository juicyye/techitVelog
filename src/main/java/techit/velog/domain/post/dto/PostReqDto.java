package techit.velog.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.post.entity.IsSecret;

public class PostReqDto {
    @Getter
    @Setter
    public static class PostReqDtoWeb {
        private String title;
        private String content;
        private Boolean isReal;
        private IsSecret isSecret;
        private String tagName;

    }
}
