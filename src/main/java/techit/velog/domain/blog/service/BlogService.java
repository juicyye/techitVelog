package techit.velog.domain.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.global.exception.CustomWebException;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    // todo 리팩토링 필수
    public BlogRespDtoWeb getBlog(String blogName) {
        Blog blog = blogRepository.findByTitle(blogName).orElseThrow(() -> new CustomWebException("블로그를 찾을수 없습니다."));
        return blogRepository.findAllByBlog(blog.getTitle());
    }

    public BlogRespDtoWeb getBlog(AccountDto accountDto) {
        Blog blog = blogRepository.findByLoginId(accountDto.getLoginId()).orElseThrow(() -> new CustomWebException("블로그를 찾을 수 없습니다."));
        return new BlogRespDtoWeb(blog);
    }
}
