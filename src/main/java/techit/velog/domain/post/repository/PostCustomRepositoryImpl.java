package techit.velog.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import techit.velog.domain.comment.entity.QComment;
import techit.velog.domain.follow.entity.QFollow;
import techit.velog.domain.liks.entity.QLikes;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.post.entity.QPosts;
import techit.velog.domain.posttag.entity.QPostTag;
import techit.velog.domain.tag.entity.QTags;
import techit.velog.domain.uploadfile.QUploadFile;
import techit.velog.domain.uploadfile.UploadFile;

import java.util.List;
import java.util.Objects;


import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.comment.entity.QComment.*;
import static techit.velog.domain.follow.entity.QFollow.*;
import static techit.velog.domain.liks.entity.QLikes.*;
import static techit.velog.domain.post.dto.PostRespDto.*;
import static techit.velog.domain.post.entity.QPosts.*;
import static techit.velog.domain.posttag.entity.QPostTag.*;
import static techit.velog.domain.tag.entity.QTags.*;
import static techit.velog.domain.uploadfile.QUploadFile.*;
import static techit.velog.domain.user.entity.QUser.*;

@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository{
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public PostCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PostRespDtoWebAll> findAllByLists(Pageable pageable) {

        List<PostRespDtoWebAll> results = queryFactory.select(Projections.fields(PostRespDtoWebAll.class,
                        posts.id.as("postId"), posts.content, posts.title, posts.createDate, posts.updateDate,posts.views,
                        posts.description.as("postDescription"), user.nickname, blog.title.as("blogName"),
                        likes.countDistinct().as("likes"), comment.countDistinct().as("comments")))
                .from(posts)
                .join(posts.blog, blog) // 블로그 없이 글을 쓸 수 없다
                .join(blog.user, user)
                .leftJoin(posts.likes, likes)
                .leftJoin(posts.comments, comment)
                .groupBy(posts.id, user.nickname, posts.title, blog.title)
                .fetch();

        int total = queryFactory.select(posts)
                .from(posts)
                .fetch().size();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public List<PostRespDtoWeb> findAllByBlogName(String blogName) {
        List<PostRespDtoWeb> results = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id.as("postId"), posts.title, posts.content,posts.views,
                        posts.createDate,posts.updateDate, blog.title.as("blogName")))
                .from(posts)
                .join(posts.blog,blog)
                .where(blog.title.eq(blogName))
                .fetch();

        for (PostRespDtoWeb result : results) {
            List<UploadFile> images = queryFactory.select(uploadFile)
                    .from(uploadFile)
                    .where(uploadFile.posts.id.eq(result.getPostId()))
                    .fetch();
            if (!images.isEmpty()) {
                result.setPostImages(images);
            }

        }
        return results;
    }

    @Override
    public PostRespDtoWeb findByIdBlogName(String blogName, String postTitle) {
        PostRespDtoWeb postRespDtoWeb = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id.as("postId"), posts.title.as("title"), posts.content, posts.createDate, posts.updateDate,posts.views,
                        blog.title.as("blogName"),user.nickname.as("nickname")))
                .from(posts)
                .join(posts.blog, blog)
                .join(blog.user, user)
                .where(posts.blog.title.eq(blogName))
                .where(posts.title.eq(postTitle))
                .fetchOne();

        List<UploadFile> uploadFiles = queryFactory.select(uploadFile)
                .from(uploadFile)
                .where(uploadFile.posts.id.eq(postRespDtoWeb.getPostId()))
                .fetch();
        if (!uploadFiles.isEmpty()) {
            postRespDtoWeb.setPostImages(uploadFiles);
        }
        int likesCount = queryFactory.select(likes)
                .from(likes)
                .join(likes.posts)
                .fetch().size();
        postRespDtoWeb.setLikes(likesCount);
        return postRespDtoWeb;
    }
    @Transactional
    public  List<PostRespDtoWebAll> test() {
        // 포스트, 블로그이름, 유저닉네임, 좋아요수, 댓글, 좋아요
        return queryFactory.select(Projections.fields(PostRespDtoWebAll.class,
                        posts.id.as("postId"), posts.content, posts.title, posts.createDate, posts.updateDate,posts.views,
                        posts.description.as("postDescription"),user.nickname, blog.title.as("blogName"),
                        likes.countDistinct().as("likes"), comment.countDistinct().as("comments")))
                .from(posts)
                .join(posts.blog, blog) // 블로그 없이 글을 쓸 수 없다
                .join(blog.user, user)
                .leftJoin(posts.likes, likes)
                .leftJoin(posts.comments, comment)
                .groupBy(posts.id, user.nickname, posts.title,blog.title)
                .fetch();
    }

    @Override
    public List<PostRespDtoWebTag> findAllByTagName(String blogName, String tagName) {
        return queryFactory.select(Projections.fields(PostRespDtoWebTag.class,
                        posts.id.as("postId"), blog.id.as("blogId"), posts.title,
                        posts.content, posts.description, posts.createDate, posts.updateDate, posts.views,
                        user.nickname, blog.title.as("blogName"), tags.name.as("tagName")
                        , likes.countDistinct().as("likes"), comment.countDistinct().as("comments"),
                tags.name.as("tagName"),postTag.countDistinct().as("tagCount")))
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

    }
}
