package techit.velog.domain.tag.repository;

import techit.velog.domain.tag.dto.TagRespDto;

import java.util.List;

import static techit.velog.domain.tag.dto.TagRespDto.*;

public interface TagRepositoryCustom {
    List<TagRespDtoWeb> findAllByBlog(String blogName);
}
