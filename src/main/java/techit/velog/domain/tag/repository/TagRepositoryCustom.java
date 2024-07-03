package techit.velog.domain.tag.repository;

import techit.velog.domain.tag.entity.Tags;

import java.util.List;

import static techit.velog.domain.tag.dto.TagRespDto.*;

public interface TagRepositoryCustom {
    List<TagRespDtoWeb> findAllByTagName(String blogName);
    List<Tags> findAllByPostId(Long blogId);
}
