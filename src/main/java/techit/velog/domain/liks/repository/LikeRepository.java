package techit.velog.domain.liks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techit.velog.domain.liks.entity.Likes;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long>, LikesCustomRepository {
    Optional<Likes> findByPosts_IdAndUser_Id(Long postId, Long id);
}
