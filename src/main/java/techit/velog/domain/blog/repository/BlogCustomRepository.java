package techit.velog.domain.blog.repository;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;

public interface BlogRepositoryCustom {
    List<BlogRespDtoWeb> findAllByBlog(String blogName);
}
