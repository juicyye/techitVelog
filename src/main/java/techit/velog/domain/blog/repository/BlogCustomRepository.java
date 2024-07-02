package techit.velog.domain.blog.repository;

import static techit.velog.domain.blog.dto.BlogRespDto.*;

public interface BlogCustomRepository {
    BlogRespDtoWeb findAllByBlog(String blogName);
}
