package techit.velog.domain.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.dto.BlogRespDto;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.post.dto.PostRespDto;
import techit.velog.domain.post.repository.PostRepository;
import techit.velog.global.exception.CustomWebException;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.post.dto.PostRespDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogRespDtoWeb getPost(String blogName) {
        Blog blog = blogRepository.findByTitle(blogName).orElseThrow(() -> new CustomWebException("블로그를 찾을수 없습니다."));
        return blogRepository.findAllByBlog(blog.getTitle());
    }
}
