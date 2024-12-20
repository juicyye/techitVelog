package techit.velog.domain.liks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.liks.entity.Likes;
import techit.velog.domain.liks.repository.LikeRepository;
import techit.velog.domain.post.dto.webresp.PostRespDtoWeb;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.repository.PostsRepository;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.exception.CustomWebException;


import java.util.Optional;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final PostsRepository postRepository;

    @Transactional
    public String like(Long postId, String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new CustomWebException("아이디가 없습니다."));
        Posts posts = postRepository.findById(postId).orElseThrow(() -> new CustomWebException("포스트를 찾을 수 없습니다."));
        Optional<Likes> _like = likeRepository.findByPosts_IdAndUser_Id(postId, user.getId());

        if(_like.isEmpty()) {
            Likes likes = new Likes(user, posts);
            likeRepository.save(likes);
        } else{
            Likes likes = _like.get();
            posts.getLikes().remove(likes);
            likeRepository.delete(likes);
        }
        return posts.getTitle();

    }

    public Page<PostRespDtoWeb> getLikes(String blogName, Pageable pageable) {
        Blog blog = blogRepository.findByTitle(blogName).orElseThrow(() -> new CustomWebException("블로그가 없습니다."));
        User user = userRepository.findByBlog_Id(blog.getId()).orElseThrow(() -> new CustomWebException("유저가 없습니다."));
        return likeRepository.findByLikePost(user.getId(), pageable);
    }
}
