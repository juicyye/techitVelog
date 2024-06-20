package techit.velog.domain.follow.repository;

import techit.velog.domain.follow.dto.FollowRespDto;

import java.util.List;

import static techit.velog.domain.follow.dto.FollowRespDto.*;

public interface FollowCustomRepository {
    List<FollowRespDtoWeb> findAllByFollowers(Long blogId);
    List<FollowRespDtoWeb> findAllByFollowings(Long blogId);
}
