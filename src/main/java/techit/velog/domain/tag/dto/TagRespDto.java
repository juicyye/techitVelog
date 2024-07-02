package techit.velog.domain.tag.dto;

import lombok.Getter;
import lombok.Setter;

public class TagRespDto {
    @Getter
    @Setter
    public static class TagRespDtoWeb {
        private Long id;
        private String name;
        private int postTagCount;

    }
}
