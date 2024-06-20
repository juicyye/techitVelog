package techit.velog.domain.blog.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static techit.velog.domain.blog.dto.BlogRespDto.*;
import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.user.entity.QUser.*;


public class BlogCustomRepositoryImpl implements BlogRepositoryCustom {
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public BlogCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<BlogRespDtoWeb> findAllByBlog(String blogName) {
        List<BlogRespDtoWeb> results = queryFactory.select(Projections.fields(BlogRespDtoWeb.class,
                        blog.id.as("blogId"), blog.title, blog.description, blog.createDate, blog.updateDate,
                        user.nickname.as("nickname"),user.uploadFile.as("image")))
                .from(blog)
                .join(blog.user, user)
                .where(blog.title.eq(blogName))
                .fetch();

        return results;
    }
}
