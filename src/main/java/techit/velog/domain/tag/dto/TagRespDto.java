package techit.velog.domain.tag.dto;

import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.post.dto.PostRespDto;

import java.util.ArrayList;
import java.util.List;

import static techit.velog.domain.post.dto.PostRespDto.*;

public class TagRespDto {
    @Getter
    @Setter
    public static class TagRespDtoWeb {
        private Long id;
        private String name;
        private int postTagCount;

    }
}
