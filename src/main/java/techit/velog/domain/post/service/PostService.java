package techit.velog.domain.post.service;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import techit.velog.domain.blog.entity.Blog;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.post.entity.IsReal;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.repository.PostsRepository;
import techit.velog.domain.posttag.entity.PostTag;
import techit.velog.domain.tag.entity.Tags;
import techit.velog.domain.tag.repository.TagRepository;
import techit.velog.domain.uploadfile.FileStore;
import techit.velog.domain.uploadfile.UploadFile;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.exception.CustomWebException;
import techit.velog.global.util.SplitService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static techit.velog.domain.post.dto.PostReqDtoWeb.*;
import static techit.velog.domain.post.dto.PostRespDtoWeb.*;
import static techit.velog.domain.user.dto.UserReqDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {
    private final PostsRepository postRepository;
    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;
    private final FileStore fileStore;
    private final UserRepository userRepository;
    private EntityManager em;

    @Transactional
    public Long create(PostReqDtoWebCreate postReqDtoWebCreate, AccountDto accountDto) {

        // 블로그 불러오고 필요한것들 바꾸기
        Blog blog = blogRepository.findByLoginId(accountDto.getLoginId()).orElseThrow(() -> new IllegalArgumentException("블로그가 없습니다."));

        List<UploadFile> uploadFiles = ImageFiles(postReqDtoWebCreate.getImageFiles());
        UploadFile uploadFile = uploadFile(postReqDtoWebCreate.getUploadFile());
        postReqDtoWebCreate.setTitle(SplitService.split(postReqDtoWebCreate.getTitle()));

        // post 저장
        Posts posts = new Posts(postReqDtoWebCreate,blog);
        posts.changeUploadFile(uploadFiles, uploadFile);
        splitTag(postReqDtoWebCreate.getTagName(),blog, posts);

        return postRepository.save(posts).getId();
    }

    public Page<PostRespDtoWebAll> getPosts(Pageable pageable) {
        return postRepository.findAllByLists(pageable);
    }

    @Transactional
    public void update(Long postId, PostReqDtoWebUpdate postReqDtoWebUpdate) {
        Posts posts = postRepository.findById(postId).orElseThrow(() -> new CustomWebException("포스트를 찾을 수 없습니다."));
        Blog blog = blogRepository.findById(postReqDtoWebUpdate.getBlogId()).orElseThrow(() -> new CustomWebException("블로그를 찾을 수 없습니다."));
        // post안에 있는 태그들을 다 삭제하고 다시 넣기
        posts.removePostTag();
        // todo 기존 태그는 삭제하고 새로운 태그는 더하기
        splitTag(postReqDtoWebUpdate.getTagName(), blog, posts);
        posts.change(postReqDtoWebUpdate);
    }

    /**
     * 태그를 split해서 태그로 저장하는 메소드
     */
    private void splitTag(String tag, Blog blog, Posts posts) {
        if (tag != null) {
            String[] split = tag.split("\\s*,\\s*|\\s+");
            HashSet<String> tags = new HashSet<>(Arrays.asList(split));

            // todo 마지막 값 중복
            for (String s : tags) {
                log.info("tag s {}", s);
                Optional<Tags> _tag = tagRepository.findByName(s);
                if (_tag.isPresent()) {
                    Tags tags1 = _tag.get();
                    new PostTag(tags1, posts);

                } else {
                    Tags tags1 = new Tags(s, blog);
                    new PostTag(tags1, posts);
                    tagRepository.save(tags1);
                }
            }
        }
    }

    /**
     * 파일저장하고 객체로 바꾸는 메소드
     */
    private List<UploadFile> ImageFiles(List<MultipartFile> files) {
        if (files != null) {
            try {
                return fileStore.storeFiles(files);
            } catch (IOException e) {
                log.error("file error {}", e.getMessage());
                throw new CustomWebException(e.getMessage());
            }
        } else {
            return null;
        }


    }

    private UploadFile uploadFile(MultipartFile file) {
        if (file != null) {
            try {
                return fileStore.storeFile(file);
            } catch (IOException e) {
                log.error("file error {}", e.getMessage());
                throw new CustomWebException(e.getMessage());
            }
        } else {
            return null;
        }
    }




    public List<PostRespDtoWebVelog> getPostsVelog(String blogName, SecurityContext securityContext) {
        if (securityContext.getAuthentication().isAuthenticated()) {
            return postRepository.findAllByVelog(blogName, true);
        } else{
            return postRepository.findAllByVelog(blogName, false);
        }

    }

    public PostRespDtoWebDetail getPostDetails(String blogName, String postTitle) {
        return postRepository.findPostDetail(blogName, postTitle);
    }

    @Transactional
    public void viewCountValidation(String blogName, String postTitle, HttpServletRequest
            request, HttpServletResponse response) {
        String cookieName = blogName + postTitle;
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElseGet(() -> new Cookie[0]);
        Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("view-count"))
                .findFirst().orElseGet(() -> {
                    addViewCount(blogName, postTitle);
                    return new Cookie("view-count", "[" + cookieName + "]");
                });
        if (!cookie.getValue().contains("[" + cookieName + "]")) {
            addViewCount(blogName, postTitle);
            cookie.setValue(cookie.getValue() + "[" + cookieName + "]");
        }
        long todayEndSecond = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
        long currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        cookie.setPath("/");
        cookie.setMaxAge((int) (todayEndSecond - currentSecond));
        response.addCookie(cookie);
    }

    private void addViewCount(String blogName, String postTitle) {
        Posts posts = postRepository.findPostBlogName(blogName, postTitle).orElseThrow(() -> new CustomWebException("포스트를 찾을 수 없습니다."));
        posts.addView(posts.getViews() + 1);
    }

    /**
     * post 업데이트 하는 메서드
     */

    public PostRespDtoWebUpdate getUpdatePost(Long postId) {
        Posts posts = postRepository.findById(postId).orElseThrow(() -> new CustomWebException("포스트를 찾을 수 없습니다."));
        PostRespDtoWebUpdate postRespDtoWebUpdate = new PostRespDtoWebUpdate(posts);
        postRespDtoWebUpdate.setTagName(changeTag(posts.getPostTags()));
        return postRespDtoWebUpdate;
    }

    /**
     * 포스트에 있는 스플릿 태그를 스트링으로 바꾸는 작업
     */
    private String changeTag(List<PostTag> postTags) {
        StringBuilder sb = new StringBuilder();
        for (PostTag postTag : postTags) {
            Tags tags = postTag.getTags();
            sb.append(tags.getName()).append(" ");
        }
        return sb.toString();
    }

    /**
     * 블로그이름이 중복되었는지 확인하는 메서드
     * 왜 잇는거지?
     * todo 왜있는지 확인하기
     */

    public boolean duplicateBlogName(PostReqDtoWebCreate postReqDtoWebCreate,AccountDto accountDto) {
        Blog blog = blogRepository.findByLoginId(accountDto.getLoginId()).orElseThrow(() -> new CustomWebException("블로그를 찾을 수 없습니다."));
        Optional<Posts> _posts = postRepository.findPostBlogName(blog.getTitle(), postReqDtoWebCreate.getTitle());
        if(_posts.isPresent()) {
            return true;
        } else{
            return false;
        }
    }

    /**
     * 임시 저장된 글만 보여주는 메소드
     */

    public List<PostRespDtoWebSave> getPostSave(AccountDto accountDto) {
        Blog blog = blogRepository.findByLoginId(accountDto.getLoginId()).orElseThrow(() -> new CustomWebException("not found blog"));
        return postRepository.findByBlog_Id(blog.getId()).stream().filter(f -> f.getIsReal() == IsReal.TEMP).map(post -> {
            PostRespDtoWebSave postRespDtoWebSave = new PostRespDtoWebSave(post);
            postRespDtoWebSave.setBlogName(blog.getTitle());
            return postRespDtoWebSave;
        }).collect(Collectors.toList());
    }

}
