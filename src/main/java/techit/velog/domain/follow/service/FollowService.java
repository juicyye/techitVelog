package techit.velog.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.follow.entity.Follow;
import techit.velog.domain.follow.repository.FollowRepository;
import techit.velog.global.exception.CustomWebException;

import java.util.List;
import java.util.Optional;

import static techit.velog.domain.follow.dto.FollowRespDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public void follow(String from_blogId, String to_blogId) throws RuntimeException {
        Blog from_blog = blogRepository.findByLoginId(from_blogId).orElseThrow(() -> new CustomWebException("팔로우할 수 있는 유저가 없습니다."));
        Blog to_blog = blogRepository.findByTitle(to_blogId).orElseThrow(() -> new CustomWebException("팔로워 수 있는 유저가 없습니다."));
        if(from_blog.getId().equals(to_blog.getId())) {
            throw new CustomWebException("자기 자신을 팔로우할 수 없습니다.");
        }
        Optional<Follow> _follow;
        _follow = followRepository.findByFollow(from_blog, to_blog);
        if(_follow.isPresent()) {
            followRepository.delete(_follow.get());
        } else{
            Follow follow = new Follow(from_blog, to_blog);
            followRepository.save(follow);
        }

    }

    public List<FollowRespDtoWeb> findByFollowers(String blogName) {
        Blog blog = blogRepository.findByTitle(blogName).orElseThrow(() -> new CustomWebException("블로그가 없습니다."));
        return followRepository.findAllByFollowers(blog.getId());


    }

    public List<FollowRespDtoWeb> findByFollowings(String blogName) {
        Blog blog = blogRepository.findByTitle(blogName).orElseThrow(() -> new CustomWebException("블로그가 없습니다."));
        return followRepository.findAllByFollowings(blog.getId());

    }
}
