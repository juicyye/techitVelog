package techit.velog.domain.tag.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import techit.velog.domain.post.dto.PostRespDto;
import techit.velog.domain.post.entity.QPosts;
import techit.velog.domain.posttag.entity.QPostTag;
import techit.velog.domain.tag.dto.TagRespDto;
import techit.velog.domain.tag.entity.QTags;

import java.util.List;

import static techit.velog.domain.post.dto.PostRespDto.*;
import static techit.velog.domain.post.entity.QPosts.*;
import static techit.velog.domain.posttag.entity.QPostTag.*;
import static techit.velog.domain.tag.dto.TagRespDto.*;
import static techit.velog.domain.tag.entity.QTags.*;

public class TagRepositoryCustomImpl implements TagRepositoryCustom {
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public TagRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TagRespDtoWeb> findAllByBlog(String blogName) {
        List<TagRespDtoWeb> results = queryFactory.select(Projections.fields(TagRespDtoWeb.class,
                        tags.id, tags.name))
                .from(tags)
                .where(tags.blog.title.eq(blogName))
                .fetch();
        for (TagRespDtoWeb result : results) {
            List<PostRespDtoWeb> tagPost = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                            posts.id))
                    .from(postTag)
                    .join(postTag.tags, tags)
                    .join(postTag.posts, posts)
                    .where(tags.id.eq(result.getId()))
                    .fetch();
            result.setPostDto(tagPost);
        }
        return results;
    }
}
