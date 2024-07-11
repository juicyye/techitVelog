package techit.velog.domain.tag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.blog.repository.BlogRepository;
import techit.velog.domain.posttag.entity.PostTag;
import techit.velog.domain.tag.dto.TagRespDtoWeb;
import techit.velog.domain.tag.entity.Tags;
import techit.velog.domain.tag.repository.TagRepository;
import techit.velog.domain.user.entity.User;
import techit.velog.domain.user.repository.UserRepository;
import techit.velog.global.dto.PrincipalDetails;
import techit.velog.global.exception.CustomWebException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TagService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public List<TagRespDtoWeb> getTagAllByBlogName(String blogName, SecurityContext securityContext) {

        if (isUser(blogName,securityContext)) {
            return tagRepository.findAllByTagName(blogName, true);
        } else{
            return tagRepository.findAllByTagName(blogName, false);
        }



    }

    @Transactional
    public void removeTag(Long blogId){
        List<Tags> tags = tagRepository.findAllByPostId(blogId);
        for (Tags tag : tags) {
            List<PostTag> postTags = tag.getPostTags();
            if(postTags.size() <= 0){
                tagRepository.delete(tag);
//                tag.removeTag();
            }
        }
    }

    private boolean isUser(String blogName, SecurityContext securityContext) {
        log.info("security context {} ", securityContext);
        Authentication authentication = securityContext.getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        Optional<User> _user = userRepository.findByBlog_Name(blogName);
        if (_user.isPresent()) {
            User user = _user.get();
            PrincipalDetails principal = (PrincipalDetails) securityContext.getAuthentication().getPrincipal();
            log.info("user : {}", principal.getUsername());
            if (user.getLoginId().equals(principal.getUsername())) {
                return true;
            }
        }

        return false;
    }
}
