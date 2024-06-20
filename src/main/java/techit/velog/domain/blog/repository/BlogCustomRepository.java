package techit.velog.domain.blog.repository;

import techit.velog.domain.post.dto.PostRespDto;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;

public interface BlogCustomRepository {
    BlogRespDtoWeb findAllByBlog(String blogName);
}
