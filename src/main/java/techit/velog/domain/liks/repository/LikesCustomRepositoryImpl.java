package techit.velog.domain.liks.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import techit.velog.domain.uploadfile.UploadFile;

import java.util.List;

import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.liks.entity.QLikes.*;
import static techit.velog.domain.post.dto.PostRespDtoWeb.*;
import static techit.velog.domain.post.entity.QPosts.*;
import static techit.velog.domain.uploadfile.QUploadFile.*;
import static techit.velog.domain.user.entity.QUser.*;

public class LikesCustomRepositoryImpl implements LikesCustomRepository{
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public LikesCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostRespDtoWebDetail> findByLikePost(Long userId) {
        List<PostRespDtoWebDetail> results = queryFactory.select(Projections.fields(PostRespDtoWebDetail.class,
                        posts.id.as("postId"), posts.title,blog.title.as("blogName")))
                .from(likes)
                .join(likes.posts, posts)
                .join(likes.user, user)
                .join(user.blog, blog)
                .where(user.id.eq(userId))
                .fetch();

        for (PostRespDtoWebDetail result : results) {
            List<UploadFile> uploadFiles = queryFactory.select(uploadFile)
                    .from(uploadFile)
                    .where(uploadFile.posts.id.eq(result.getPostId()))
                    .fetch();
            result.setPostImages(uploadFiles);
        }
        return results;
    }
}
