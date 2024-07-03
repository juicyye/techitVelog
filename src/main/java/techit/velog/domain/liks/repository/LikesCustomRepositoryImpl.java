package techit.velog.domain.liks.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import techit.velog.domain.uploadfile.QUploadFile;
import techit.velog.domain.uploadfile.UploadFile;

import java.util.List;

import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.comment.entity.QComment.comment;
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
    public List<PostRespDtoWebAll> findByLikePost(Long userId) {
        QUploadFile userImage = new QUploadFile("userImage");

        return queryFactory.select(Projections.fields(PostRespDtoWebAll.class,
                        posts.id.as("postId"), posts.title, posts.createDate, posts.updateDate,posts.views,
                        posts.description.as("postDescription"), user.nickname, blog.title.as("blogName"),
                        likes.countDistinct().as("likes"), comment.countDistinct().as("comments"),
                        posts.uploadFile.as("postImage"), userImage.as("userImage")))
                .from(likes)
                .join(likes.posts, posts)
                .join(likes.user, user)
                .join(user.blog, blog)
                .leftJoin(user.uploadFile, userImage)
                .leftJoin(posts.comments, comment)
                .leftJoin(posts.uploadFile, uploadFile)
                .where(user.id.eq(userId))
                .groupBy(posts.id, user.nickname, posts.title, blog.title)
                .fetch();
    }
}
