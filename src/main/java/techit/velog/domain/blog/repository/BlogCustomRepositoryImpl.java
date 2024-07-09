package techit.velog.domain.blog.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static techit.velog.domain.blog.dto.BlogRespDtoWeb.*;
import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.follow.entity.QFollow.follow;
import static techit.velog.domain.uploadfile.QUploadFile.*;
import static techit.velog.domain.user.entity.QUser.*;


public class BlogCustomRepositoryImpl implements BlogCustomRepository {
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public BlogCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public BlogRespDtoWebBasic findAllByBlog(String blogName) {
        BlogRespDtoWebBasic result = queryFactory.select(Projections.fields(BlogRespDtoWebBasic.class,
                        blog.id.as("blogId"), blog.title, blog.description, blog.createDate, blog.updateDate,
                        user.nickname.as("nickname"), user.id.as("userId"), blog.title.as("blogName"),user.uploadFile.as("userImage"), user.loginId.as("loginId")))
                .from(blog)
                .join(blog.user, user)
                .leftJoin(user.uploadFile, uploadFile)
                .where(blog.title.eq(blogName))
                .fetchOne();


        int followingCount = queryFactory.select(follow)
                .from(follow)
                .where(follow.from_blog.id.eq(result.getBlogId()))
                .fetch().size();
        result.setFollowings(followingCount);

        int followerCount = queryFactory.select(follow)
                .from(follow)
                .where(follow.to_blog.id.eq(result.getBlogId()))
                .fetch().size();
        result.setFollowers(followerCount);

        return result;
    }
}
