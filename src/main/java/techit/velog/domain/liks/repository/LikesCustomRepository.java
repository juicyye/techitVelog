package techit.velog.domain.liks.repository;

import java.util.List;

import static techit.velog.domain.post.dto.PostRespDto.*;

public interface LikesCustomRepository {
    List<PostRespDtoWeb> findByLikePost(Long userId);
}
