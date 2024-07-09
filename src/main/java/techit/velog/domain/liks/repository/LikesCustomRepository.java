package techit.velog.domain.liks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static techit.velog.domain.post.dto.PostRespDtoWeb.*;

public interface LikesCustomRepository {
    Page<PostRespDtoWebAll> findByLikePost(Long userId, Pageable pageable);
}
