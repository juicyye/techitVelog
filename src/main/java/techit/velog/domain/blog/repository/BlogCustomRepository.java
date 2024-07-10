package techit.velog.domain.blog.repository;

import techit.velog.domain.blog.dto.BlogRespDtoWeb;

import static techit.velog.domain.blog.dto.BlogRespDtoWeb.*;

public interface BlogCustomRepository {
    BlogRespDtoWeb findAllByBlog(String blogName);
}
