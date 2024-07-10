package techit.velog.domain.post.dto.webreq;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.post.entity.IsSecret;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostReqDtoWebCreate {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9가-힣ㄱ-ㅎ]{2,20}$",message = "제목은 2~20 이내로 적어주세요")
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,20}", message = "2~20자 이내로 적어주세요")
    private String postDescription;
    private IsSecret isSecret;
    private Boolean isTemp;
    private String tagName;
    private MultipartFile imageFile;
    private List<MultipartFile> imageFiles = new ArrayList<>();
}
