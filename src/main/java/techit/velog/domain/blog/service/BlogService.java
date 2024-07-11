package techit.velog.domain.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.dto.BlogRespDtoWeb;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.global.dto.PrincipalDetails;
import techit.velog.global.exception.CustomWebException;

import static techit.velog.domain.blog.dto.BlogRespDtoWeb.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    private final BlogRepository blogRepository;


    public BlogRespDtoWeb getBlog(String blogName) {

        return blogRepository.findAllByBlog(blogName);
    }

    public BlogRespDtoWeb getBlog(PrincipalDetails principalDetails) {
        Blog blog = blogRepository.findByLoginId(principalDetails.getUsername()).orElseThrow(() -> new CustomWebException("블로그를 찾을 수 없습니다."));
        log.info("blogService blogId: {}", blog.getId());
        return new BlogRespDtoWeb(blog);
    }
}
