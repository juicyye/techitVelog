package techit.velog.domain.blog.repository;

import static techit.velog.domain.blog.dto.BlogRespDtoWeb.*;

public interface BlogCustomRepository {
    BlogRespDtoWebBasic findAllByBlog(String blogName);
}
