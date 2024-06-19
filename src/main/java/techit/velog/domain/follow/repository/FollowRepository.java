package techit.velog.domain.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techit.velog.domain.follow.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
