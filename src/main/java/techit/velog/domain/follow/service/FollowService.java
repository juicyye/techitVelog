package techit.velog.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.follow.entity.Follow;
import techit.velog.domain.follow.repository.FollowRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public String follow(Long followerId, Long followeeId) throws RuntimeException {
        Blog follower = blogRepository.findById(followerId).orElseThrow(RuntimeException::new);
        Blog following = blogRepository.findById(followeeId).orElseThrow(RuntimeException::new);

        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
        return "ok";
    }
}
