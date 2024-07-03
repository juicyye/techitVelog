package techit.velog.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.posttag.entity.PostTag;
import techit.velog.domain.tag.entity.Tags;
import techit.velog.domain.tag.repository.TagRepository;

import java.util.List;

import static techit.velog.domain.tag.dto.TagRespDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagRespDtoWeb> getTagAllByBlogName(String blogName) {
        return tagRepository.findAllByTagName(blogName);
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
}
