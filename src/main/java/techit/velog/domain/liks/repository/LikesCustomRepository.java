package techit.velog.domain.liks.repository;

import java.util.List;

import static techit.velog.domain.post.dto.PostRespDtoWeb.*;

public interface LikesCustomRepository {
    List<PostRespDtoWebAll> findByLikePost(Long userId);
}
