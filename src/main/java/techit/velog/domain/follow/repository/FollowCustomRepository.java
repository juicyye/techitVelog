package techit.velog.domain.follow.repository;

import techit.velog.domain.follow.dto.FollowRespDtoWeb;

import java.util.List;

public interface FollowCustomRepository {
    List<FollowRespDtoWeb> findAllByFollowers(Long blogId);
    List<FollowRespDtoWeb> findAllByFollowings(Long blogId);
}
