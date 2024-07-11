package techit.velog.domain.liks.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import techit.velog.domain.post.dto.webresp.PostRespDtoWeb;
import techit.velog.domain.uploadfile.QUploadFile;
import techit.velog.domain.user.entity.QUser;

import java.util.List;

import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.comment.entity.QComment.comment;
import static techit.velog.domain.liks.entity.QLikes.*;

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
    public Page<PostRespDtoWeb> findByLikePost(Long userId, Pageable pageable) {
        QUploadFile userImage = new QUploadFile("userImage");
        QUser crushUser = new QUser("crushUser");

        List<PostRespDtoWeb> results = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id.as("postId"), posts.title, posts.createDate, posts.updateDate, posts.views,
                        posts.description.as("postDescription"), crushUser.nickname, blog.title.as("blogName"),
                        likes.countDistinct().as("likes"), comment.countDistinct().as("comments"),
                        posts.uploadFile.as("postImage"), userImage.as("userImage")))
                .from(likes)
                .join(likes.posts, posts)
                .join(likes.user, user)
                .join(posts.blog, blog)
                .join(blog.user, crushUser)
                .leftJoin(crushUser.uploadFile, userImage)
                .leftJoin(posts.comments, comment)
                .leftJoin(posts.uploadFile, uploadFile)
                .where(user.id.eq(userId))
                .groupBy(posts.id, posts.title, blog.title)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(posts.createDate.desc())
                .fetch();

        int total = queryFactory.select(posts)
                .from(likes)
                .join(likes.user, user)
                .join(likes.posts, posts)
                .where(user.id.eq(userId))
                .fetch().size();
        return new PageImpl<>(results, pageable, total);
    }
}
