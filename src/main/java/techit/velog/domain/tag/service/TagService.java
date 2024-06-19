package techit.velog.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.tag.dto.TagRespDto;
import techit.velog.domain.tag.repository.TagRepository;

import java.util.List;

import static techit.velog.domain.tag.dto.TagRespDto.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagRespDtoWeb> getTagAllByBlogName(String blogName) {
        return tagRepository.findAllByBlog(blogName);

    }
}
