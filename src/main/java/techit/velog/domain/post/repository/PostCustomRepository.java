package techit.velog.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import techit.velog.domain.post.dto.PostRespDto;
import techit.velog.domain.post.entity.Posts;

import java.util.List;

import static techit.velog.domain.post.dto.PostRespDto.*;

public interface PostCustomRepository {
    Page<PostRespDtoWeb> findAllByLists(Pageable pageable);
    List<PostRespDtoWeb> findAllByBlogName(String blogName);

    PostRespDtoWeb findByIdBlogName(String blogName, Long postId);
}
