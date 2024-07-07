package techit.velog.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import techit.velog.domain.post.dto.PostSortType;

import java.util.List;

import static techit.velog.domain.post.dto.PostRespDtoWeb.*;

public interface PostsCustomRepository {
    Page<PostRespDtoWebAll> findAllByLists(Pageable pageable, PostSortType postSortType);
    List<PostRespDtoWebVelog> findAllByVelog(String blogName, boolean isUser);

    PostRespDtoWebDetail findPostDetail(String blogName, String postTitle);

    List<PostRespDtoWebVelog> findPostsByTagName(String blogName, String tagName, boolean isUser);



}
