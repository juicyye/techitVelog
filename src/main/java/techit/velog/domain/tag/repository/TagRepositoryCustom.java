package techit.velog.domain.tag.repository;

import techit.velog.domain.tag.dto.TagRespDtoWeb;
import techit.velog.domain.tag.entity.Tags;

import java.util.List;

public interface TagRepositoryCustom {
    List<TagRespDtoWeb> findAllByTagName(String blogName,Boolean isUser);
    List<Tags> findAllByPostId(Long blogId);
}
