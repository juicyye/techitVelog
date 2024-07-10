package techit.velog.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRespDtoWeb {
    private Long id;
    private String name;
    private int postTagCount;
}
