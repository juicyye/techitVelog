package techit.velog.domain.tag.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import techit.velog.domain.post.entity.IsReal;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.tag.dto.TagRespDtoWeb;
import techit.velog.domain.tag.entity.Tags;


import java.util.List;


import static techit.velog.domain.blog.entity.QBlog.*;

import static techit.velog.domain.post.entity.QPosts.*;
import static techit.velog.domain.posttag.entity.QPostTag.*;
import static techit.velog.domain.tag.entity.QTags.*;

public class TagRepositoryCustomImpl implements TagRepositoryCustom {
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public TagRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<TagRespDtoWeb> findAllByTagName(String blogName,Boolean isUser) {

        List<TagRespDtoWeb> results = queryFactory.select(Projections.fields(TagRespDtoWeb.class,
                        tags.id.as("id"),tags.name.as("name")))
                .from(postTag)
                .join(postTag.tags, tags)
                .join(postTag.posts, posts)
                .join(tags.blog, blog)
                .where(blog.title.eq(blogName),posts.isReal.stringValue().eq(IsReal.REAL.name())
                        ,isUser(isUser))
                .fetch();
        for (TagRespDtoWeb result : results) {
            int size = queryFactory.select(postTag.id)
                    .from(postTag)
                    .leftJoin(postTag.tags, tags)
                    .where(tags.id.eq(result.getId()))
                    .fetch().size();
            result.setPostTagCount(size);
        }
        return results;
    }

    @Override
    public List<Tags> findAllByPostId(Long blogId) {
        return queryFactory.select(tags)
                .from(tags)
                .join(tags.blog, blog)
                .where(blog.id.eq(blogId))
                .fetch();
    }

    private BooleanExpression isUser(Boolean isUser) {
        return isUser ? null : posts.isSecret.stringValue().eq(IsSecret.NORMAL.name());
    }
}
