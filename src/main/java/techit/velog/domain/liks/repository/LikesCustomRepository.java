package techit.velog.domain.liks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import techit.velog.domain.post.dto.webresp.PostRespDtoWeb;


public interface LikesCustomRepository {
    Page<PostRespDtoWeb> findByLikePost(Long userId, Pageable pageable);
}
