package techit.velog.domain.post.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import techit.velog.domain.follow.entity.QFollow;
import techit.velog.domain.liks.entity.QLikes;
import techit.velog.domain.post.entity.Posts;
import techit.velog.domain.posttag.entity.QPostTag;
import techit.velog.domain.uploadfile.UploadFile;

import java.util.List;


import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.follow.entity.QFollow.*;
import static techit.velog.domain.liks.entity.QLikes.*;
import static techit.velog.domain.post.dto.PostRespDto.*;
import static techit.velog.domain.post.entity.QPosts.*;
import static techit.velog.domain.posttag.entity.QPostTag.*;
import static techit.velog.domain.uploadfile.QUploadFile.*;
import static techit.velog.domain.user.entity.QUser.*;


public class PostCustomRepositoryImpl implements PostCustomRepository{
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public PostCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PostRespDtoWeb> findAllByLists(Pageable pageable) {
        List<PostRespDtoWeb> results = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id, posts.title, posts.content,posts.blog.title.as("blogName"),
                        posts.createDate,posts.updateDate,posts.views))
                .from(posts)
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

        int total = queryFactory.select(posts)
                .from(posts)
                .fetch().size();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public List<PostRespDtoWeb> findAllByBlogName(String blogName) {
        List<PostRespDtoWeb> results = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id.as("postId"), posts.title, posts.content,
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
                        posts.id.as("postId"), posts.title.as("title"), posts.content, posts.createDate, posts.updateDate,
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
}
