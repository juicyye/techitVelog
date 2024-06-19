package techit.velog.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.repository.PostRepository;
import techit.velog.domain.posttag.entity.PostTag;
import techit.velog.domain.tag.entity.Tags;
import techit.velog.domain.tag.repository.TagRepository;
import techit.velog.domain.uploadfile.FileStore;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.global.exception.CustomWebException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static techit.velog.domain.post.dto.PostReqDto.*;
import static techit.velog.domain.post.dto.PostRespDto.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;
    private final FileStore fileStore;

    @Transactional
    public Long create(PostReqDtoWeb postReqDtoWeb, AccountDto accountDto) {
        // 블로그 불러오고 필요한것들 바꾸기
        Blog blog = blogRepository.findByUserId(accountDto.getLoginId()).orElseThrow(() -> new IllegalArgumentException("블로그가 없습니다."));
        List<UploadFile> uploadFiles = ImageFiles(postReqDtoWeb.getImageFiles());
        UploadFile uploadFile = uploadFile(postReqDtoWeb.getUploadFile());

        // post 저장
        Posts posts = new Posts(postReqDtoWeb, blog);
        splitTag(postReqDtoWeb.getTagName(),blog,posts);
        posts.changeUploadFile(uploadFiles,uploadFile);
        Posts savedPost = postRepository.save(posts);
        return savedPost.getId();
    }

    public Page<PostRespDtoWeb> getPosts(Pageable pageable) {
        return postRepository.findAllByLists(pageable);
    }


    /**
     * 태그를 split해서 태그로 저장하는 메소드
     */
    private void splitTag(String tag, Blog blog, Posts posts) {
        if (tag != null) {
            String[] split = tag.split("\\s*,\\s*|\\s+");

            // todo 나중에 변수명 변경
            for (String s : split) {
                Optional<Tags> _tag = tagRepository.findByName(s);
                if (_tag.isEmpty()) {
                    Tags tags1 = new Tags(s, blog);
                    PostTag.ChangeTag(tags1,posts);
                    tagRepository.save(tags1);
                } else{
                    Tags tags1 = _tag.get();
                    PostTag.ChangeTag(tags1,posts);
                }
            }
        }

    }

    /**
     * 포스트태그에 태그를 저장하는 메소드
     */
    /*private List<PostTag> PostTags(String tag, Blog blog, Posts posts) {
        if (tag != null) {
            List<PostTag> postTags = new ArrayList<>();
            List<Tags> tags = splitTag(tag, blog);
            // todo 나중에 변수명이랑 스트림함수 써서 리팩토링
            for (Tags tag1 : tags) {
                postTags.add(new PostTag(tag1,posts));
            }
            return postTags;
        } else return null;

    }*/

    /**
     * 파일저장하고 객체로 바꾸는 메소드
     */
    private List<UploadFile> ImageFiles(List<MultipartFile> files) {
        if (files != null) {
            try{
                return fileStore.storeFiles(files);
            }  catch (IOException e) {
                log.error("file error {}",e.getMessage());
                throw new CustomWebException(e.getMessage());
            }
        } else{
            return null;
        }


    }

    private UploadFile uploadFile(MultipartFile file) {
        if (file != null) {
            try{
                return fileStore.storeFile(file);
            }  catch (IOException e) {
                log.error("file error {}",e.getMessage());
                throw new CustomWebException(e.getMessage());
            }
        } else{
            return null;
        }

    }


    public List<PostRespDtoWeb> getPostByBlog(String blogName) {
        return postRepository.findAllByBlogName(blogName);

    }
}
