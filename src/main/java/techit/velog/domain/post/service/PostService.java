package techit.velog.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.repository.PostRepository;
import techit.velog.domain.posttag.entity.PostTag;
import techit.velog.domain.tag.entity.Tags;
import techit.velog.domain.tag.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

import static techit.velog.domain.post.dto.PostReqDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Long create(PostReqDtoWeb postReqDtoWeb, AccountDto accountDto) {
        Blog blog = blogRepository.findByUserId(accountDto.getLoginId()).orElseThrow(() -> new IllegalArgumentException("블로그가 없습니다."));
        List<PostTag> postTags = PostTags(postReqDtoWeb.getTagName(), blog);

        Posts posts = new Posts(postReqDtoWeb, blog, postTags);
        Posts savedPost = postRepository.save(posts);
        return savedPost.getId();
    }



    private List<Tags> splitTag(String tag, Blog blog) {
        String[] split = tag.split("\\s*,\\s*|\\s+");
        List<Tags> tags = new ArrayList<>();
        // todo 나중에 변수명 변경
        for (String s : split) {
            Tags tags1 = new Tags(s, blog);
            if (!tagRepository.existsByName(s)) {
                tagRepository.save(tags1);
            }
            tags.add(tags1);
        }
        return tags;
    }

    private List<PostTag> PostTags(String tag, Blog blog) {
        List<PostTag> postTags = new ArrayList<>();
        List<Tags> tags = splitTag(tag, blog);
        // todo 나중에 변수명이랑 스트림함수 써서 리팩토링
        for (Tags tag1 : tags) {
            postTags.add(new PostTag(tag1));
        }
        return postTags;
    }

}
