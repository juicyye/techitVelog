package techit.velog.domain.blog.dto;

import lombok.Getter;
import lombok.Setter;
import techit.velog.domain.post.dto.PostRespDto;
import techit.velog.domain.tag.dto.TagRespDto;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.dto.UserRespDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static techit.velog.domain.post.dto.PostRespDto.*;
import static techit.velog.domain.tag.dto.TagRespDto.*;
import static techit.velog.domain.user.dto.UserRespDto.*;

public class BlogRespDto {
    @Getter
    @Setter
    public static class BlogRespDtoWeb{
        private Long blogId;
        private String nickname;
        private String title;
        private String description;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private UploadFile image;
        private List<UserRespDtoWeb> follwing = new ArrayList<>();
    }
}
