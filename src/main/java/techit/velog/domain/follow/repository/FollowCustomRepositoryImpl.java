package techit.velog.domain.follow.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import techit.velog.domain.follow.dto.FollowRespDtoWeb;

import java.util.List;

import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.follow.entity.QFollow.*;
import static techit.velog.domain.uploadfile.QUploadFile.*;
import static techit.velog.domain.user.entity.QUser.*;

public class FollowCustomRepositoryImpl implements FollowCustomRepository{
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public FollowCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<FollowRespDtoWeb> findAllByFollowers(Long blogId) {

        return queryFactory.select(Projections.fields(FollowRespDtoWeb.class,
                        follow.id.as("followId"), blog.description, blog.title.as("blogName"), user.nickname,
                        uploadFile.as("userImage")))
                .from(follow)
                .join(follow.from_blog, blog)
                .join(blog.user, user)
                .leftJoin(user.uploadFile, uploadFile)
                .where(follow.to_blog.id.eq(blogId))
                .fetch();
    }

    @Override
    public List<FollowRespDtoWeb> findAllByFollowings(Long blogId) {
        return queryFactory.select(Projections.fields(FollowRespDtoWeb.class,
                        follow.id.as("followId"), blog.description, blog.title.as("blogName"), user.nickname,
                        uploadFile.as("userImage")))
                .from(follow)
                .join(follow.to_blog, blog)
                .join(blog.user, user)
                .leftJoin(user.uploadFile, uploadFile)
                .where(follow.from_blog.id.eq(blogId))
                .fetch();
    }
}
