package techit.velog.domain.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import techit.velog.domain.post.dto.PostSearch;
import techit.velog.domain.post.dto.PostSortType;
import techit.velog.domain.post.dto.webresp.PostRespDtoWeb;

import java.util.List;

public interface PostsCustomRepository {
    Page<PostRespDtoWeb> findAllByLists(Pageable pageable, PostSortType postSortType, PostSearch search);
    List<PostRespDtoWeb> findAllByVelog(String blogName, boolean isUser, PostSearch postSearch);

    PostRespDtoWeb findPostDetail(String blogName, String postTitle);

    List<PostRespDtoWeb> findPostsByTagName(String blogName, String tagName, boolean isUser);



}
