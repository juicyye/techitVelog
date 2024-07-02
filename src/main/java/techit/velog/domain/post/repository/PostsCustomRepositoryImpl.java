package techit.velog.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import techit.velog.domain.post.entity.IsReal;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.uploadfile.QUploadFile;

import java.util.List;


import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.comment.entity.QComment.*;
import static techit.velog.domain.liks.entity.QLikes.*;
import static techit.velog.domain.post.dto.PostRespDtoWeb.*;
import static techit.velog.domain.post.entity.QPosts.*;
import static techit.velog.domain.posttag.entity.QPostTag.*;
import static techit.velog.domain.tag.entity.QTags.*;
import static techit.velog.domain.uploadfile.QUploadFile.*;
import static techit.velog.domain.user.entity.QUser.*;

@Repository
public class PostsCustomRepositoryImpl implements PostsCustomRepository {
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public PostsCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PostRespDtoWebAll> findAllByLists(Pageable pageable) {
        QUploadFile userImage = new QUploadFile("userImage");

        List<PostRespDtoWebAll> results = queryFactory.select(Projections.fields(PostRespDtoWebAll.class,
                        posts.id.as("postId"), posts.content, posts.title, posts.createDate, posts.updateDate,posts.views,
                        posts.description, user.nickname, blog.title.as("blogName"),
                        likes.countDistinct().as("likes"), comment.countDistinct().as("comments"),
                        posts.uploadFile.as("postImage"), userImage.as("userImage")))
                .from(posts)
                .join(posts.blog, blog)
                .join(blog.user, user)
                .leftJoin(user.uploadFile, userImage)
                .leftJoin(posts.likes, likes)
                .leftJoin(posts.comments, comment)
                .leftJoin(posts.uploadFile, uploadFile)
                .groupBy(posts.id, user.nickname, posts.title, blog.title)
                .where(posts.isSecret.stringValue().eq(IsSecret.NORMAL.name()).and(posts.isReal.stringValue().eq(IsReal.REAL.name())))
                .fetch();

        int total = queryFactory.select(posts)
                .from(posts)
                .where(posts.isSecret.stringValue().eq(IsSecret.NORMAL.name()).and(posts.isReal.stringValue().eq(IsReal.REAL.name())))
                .fetch().size();

        return new PageImpl<>(results, pageable, total);
    }

    public List<PostRespDtoWebVelog> findAllByVelog(String blogName, boolean isuser) {
        QUploadFile userImage = new QUploadFile("userImage");
        List<PostRespDtoWebVelog> results = queryFactory.select(Projections.fields(PostRespDtoWebVelog.class,
                        posts.id.as("postId"), posts.title,posts.views, posts.description.as("postDescription"), posts.uploadFile.as("postImage"),
                        posts.createDate,posts.updateDate, blog.title.as("blogName"), likes.countDistinct().as("likes"), comment.countDistinct().as("comments"), posts.isSecret,
                        user.nickname, userImage.as("userImage")))
                .from(posts)
                .join(posts.blog,blog)
                .join(blog.user,user)
                .leftJoin(posts.uploadFile, uploadFile)
                .leftJoin(posts.likes, likes)
                .leftJoin(posts.comments, comment)
                .leftJoin(user.uploadFile, userImage)
                .where(blog.title.eq(blogName))
                .where(isUser(isuser),posts.isReal.stringValue().eq(IsReal.REAL.name()))
                .fetch();

        for (PostRespDtoWebVelog result : results) {
            List<String> tagList = queryFactory.select(tags.name)
                    .from(postTag)
                    .join(postTag.tags, tags)
                    .join(postTag.posts, posts)
                    .where(posts.id.eq(result.getPostId()))
                    .fetch();
            result.setTagName(tagList);
        }
        return results;
    }



    @Override
    public PostRespDtoWebDetail findPostDetail(String blogName, String postTitle) {
        PostRespDtoWebDetail postRespDtoWebDetail = queryFactory.select(Projections.fields(PostRespDtoWebDetail.class,
                        posts.id.as("postId"), posts.title.as("title"), posts.content, posts.createDate, posts.updateDate, posts.views,
                        blog.title.as("blogName"), user.nickname.as("nickname"), posts.uploadFiles.as("postImages")))
                .from(posts)
                .join(posts.blog, blog)
                .join(blog.user, user)
                .leftJoin(posts.uploadFiles, uploadFile)
                .leftJoin(posts.postTags, postTag)
                .where(posts.blog.title.eq(blogName), posts.title.eq(postTitle), posts.isSecret.stringValue().eq(IsSecret.NORMAL.name()), posts.isReal.stringValue().eq(IsReal.REAL.name()))
                .fetchOne();

        int likesCount = queryFactory.select(likes)
                .from(likes)
                .join(likes.posts)
                .fetch().size();
        postRespDtoWebDetail.setLikes(likesCount);
        return postRespDtoWebDetail;
    }

    @Override
    public List<PostRespDtoWebVelog> findPostsByTagName(String blogName, String tagName, boolean isUser) {
        QUploadFile userImage = new QUploadFile("userImage");
        List<PostRespDtoWebVelog> results = queryFactory.select(Projections.fields(PostRespDtoWebVelog.class,
                        posts.id.as("postId"), posts.title,posts.views, posts.description.as("postDescription"), posts.uploadFile.as("postImage"),
                        posts.createDate,posts.updateDate, blog.title.as("blogName"), likes.countDistinct().as("likes"), comment.countDistinct().as("comments"), posts.isSecret,
                        user.nickname, userImage.as("userImage")))
                .from(postTag)
                .join(postTag.tags, tags)
                .join(postTag.posts, posts)
                .join(posts.blog, blog)
                .join(blog.user, user)
                .leftJoin(posts.likes, likes)
                .leftJoin(posts.comments, comment)
                .where(blog.title.eq(blogName).and(tags.name.eq(tagName)))
                .groupBy(posts.id, user.nickname, posts.title,blog.title)
                .fetch();

        for (PostRespDtoWebVelog result : results) {
            List<String> tagList = queryFactory.select(tags.name)
                    .from(postTag)
                    .join(postTag.tags, tags)
                    .join(postTag.posts, posts)
                    .where(posts.id.eq(result.getPostId()))
                    .fetch();
            result.setTagName(tagList);
        }
        return results;
    }

    private BooleanExpression isTemp(Boolean temp) {
        return temp ? posts.isReal.stringValue().eq(IsReal.TEMP.name()) : posts.isReal.stringValue().eq(IsReal.REAL.name());
    }

    private BooleanExpression isUser(Boolean isuser) {
        return isuser ? null : posts.isSecret.stringValue().eq(IsSecret.NORMAL.name());
    }

}
