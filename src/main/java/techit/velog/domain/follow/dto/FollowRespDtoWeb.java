package techit.velog.domain.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import techit.velog.domain.uploadfile.UploadFile;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowRespDtoWeb {

    private Long followId;
    private String nickname;
    private String description;
    private String blogName;
    private UploadFile userImage;
}
