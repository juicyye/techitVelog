package techit.velog.domain.blog.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.blog.entity.QBlog.*;


public class BlogRepositoryCustomImpl implements BlogRepositoryCustom {
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public BlogRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BlogRespDtoWeb> findAllByBlog(String blogName) {
        List<BlogRespDtoWeb> results = queryFactory.select(Projections.fields(BlogRespDtoWeb.class,
                        blog.id.as("blogId"), blog.title, blog.description, blog.createDate, blog.updateDate,
                        blog.user.nickname.as("nickname"),blog.user.uploadFile.as("image")))
                .from(blog)
                .where(blog.title.eq(blogName))
                .fetch();

        return results;
    }
}
