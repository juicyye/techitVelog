package techit.velog.domain.liks.repository;

import java.util.List;

import static techit.velog.domain.post.dto.PostRespDtoWeb.*;

public interface LikesCustomRepository {
    List<PostRespDtoWebDetail> findByLikePost(Long userId);
}
