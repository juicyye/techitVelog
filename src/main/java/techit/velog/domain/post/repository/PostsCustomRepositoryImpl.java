package techit.velog.domain.post.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import techit.velog.domain.post.dto.PostSearch;
import techit.velog.domain.post.dto.PostSortType;
import techit.velog.domain.post.dto.webresp.PostRespDtoWeb;
import techit.velog.domain.post.entity.IsReal;
import techit.velog.domain.post.entity.IsSecret;
import techit.velog.domain.uploadfile.QUploadFile;
import techit.velog.domain.uploadfile.UploadFile;

import java.util.List;


import static techit.velog.domain.blog.entity.QBlog.*;
import static techit.velog.domain.comment.entity.QComment.*;
import static techit.velog.domain.liks.entity.QLikes.*;
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
    public Page<PostRespDtoWeb> findAllByLists(Pageable pageable, PostSortType postSortType, PostSearch postSearch) {
        QUploadFile userImage = new QUploadFile("userImage");
        OrderSpecifier orderSpecifier = createOrderSpecifier(postSortType);

        List<PostRespDtoWeb> results = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id.as("postId"), posts.title, posts.createDate, posts.updateDate,posts.views,
                        posts.description.as("postDescription"), user.nickname, blog.title.as("blogName"),
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
                .where(posts.isSecret.stringValue().eq(IsSecret.NORMAL.name()).and(posts.isReal.stringValue().eq(IsReal.REAL.name())),search(postSearch))
                .orderBy(orderSpecifier, posts.createDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        int total = queryFactory.select(posts)
                .from(posts)
                .where(posts.isSecret.stringValue().eq(IsSecret.NORMAL.name()).and(posts.isReal.stringValue().eq(IsReal.REAL.name())))
                .fetch().size();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression search(PostSearch search) {
        if (search.getKey() != null) {
            if (search.getKey().equals("title")) {
                return posts.title.contains(search.getValue()).or(posts.content.contains(search.getValue()));
            } else if (search.getKey().equals("name")) {
                return user.name.eq(search.getValue());
            }
        }
        return null;
    }

    private OrderSpecifier createOrderSpecifier(PostSortType sortType) {
        return switch (sortType) {
            case PostSortType.OLDEST -> new OrderSpecifier(Order.ASC, posts.createDate);
            case PostSortType.LIKES -> new OrderSpecifier(Order.DESC, likes.countDistinct());
            case PostSortType.NEWEST -> new OrderSpecifier(Order.DESC, posts.createDate);
            default -> new OrderSpecifier(Order.DESC, posts.createDate);
        };
    }

    public List<PostRespDtoWeb> findAllByVelog(String blogName, boolean isuser, PostSearch search) {
        List<PostRespDtoWeb> results = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id.as("postId"), posts.title,posts.views, posts.description.as("postDescription"), posts.uploadFile.as("postImage"),
                        posts.createDate,posts.updateDate, likes.countDistinct().as("likes"), comment.countDistinct().as("comments"), posts.isSecret, blog.title.as("blogName")
                        ))
                .from(posts)
                .join(posts.blog,blog)
                .leftJoin(posts.uploadFile, uploadFile)
                .leftJoin(posts.likes, likes)
                .leftJoin(posts.comments, comment)
                .groupBy(posts.id, blog.title)
                .where(blog.title.eq(blogName),isUser(isuser),posts.isReal.stringValue().eq(IsReal.REAL.name()),postSearch(search))
                .orderBy(posts.createDate.desc())
                .fetch();

        return getTagNames(results);
    }

    private BooleanExpression postSearch(PostSearch postSearch) {
        if (postSearch.getValue() != null) {
            return posts.title.contains(postSearch.getValue()).or(posts.content.contains(postSearch.getValue()));
        } else{
            return null;
        }
    }



    @Override
    public List<PostRespDtoWeb> findPostsByTagName(String blogName, String tagName, boolean isUser) {
        List<PostRespDtoWeb> results = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id.as("postId"), posts.title,posts.views, posts.description.as("postDescription"), uploadFile.as("postImage"),
                        posts.createDate,posts.updateDate, likes.countDistinct().as("likes"), comment.countDistinct().as("comments"), posts.isSecret, blog.title.as("blogName")))
                .from(postTag)
                .join(postTag.tags, tags)
                .join(postTag.posts, posts)
                .join(posts.blog, blog)
                .leftJoin(posts.likes, likes)
                .leftJoin(posts.comments, comment)
                .leftJoin(posts.uploadFile, uploadFile)
                .where(blog.title.eq(blogName).and(tags.name.eq(tagName)),isUser(isUser))
                .groupBy(posts.id, blog.title)
                .fetch();

        return getTagNames(results);
    }

    private List<PostRespDtoWeb> getTagNames(List<PostRespDtoWeb> results) {
        for (PostRespDtoWeb result : results) {
            List<String> tagList = queryFactory.select(tags.name)
                    .from(postTag)
                    .join(postTag.tags, tags)
                    .join(postTag.posts, posts)
                    .where(posts.id.eq(result.getPostId()))
                    .fetch();
            result.setTagNames(tagList);
        }
        return results;
    }

    @Override
    public PostRespDtoWeb findPostDetail(String blogName, String postTitle) {
        PostRespDtoWeb result = queryFactory.select(Projections.fields(PostRespDtoWeb.class,
                        posts.id.as("postId"), posts.title, posts.content, posts.createDate, posts.updateDate, posts.views, posts.description.as("postDescription"),
                        blog.title.as("blogName"), likes.countDistinct().as("likes"),posts.isSecret, posts.isReal, user.loginId))
                .from(posts)
                .join(posts.blog, blog)
                .join(blog.user, user)
                .leftJoin(posts.likes, likes)
                .where(blog.title.eq(blogName), posts.title.eq(postTitle))
                .groupBy(posts.id, posts.title, blog.title)
                .fetchOne();

        List<UploadFile> uploadFiles = queryFactory.select(uploadFile)
                .from(uploadFile)
                .join(uploadFile.posts, posts)
                .where(posts.id.eq(result.getPostId()))
                .fetch();
        result.setPostImages(uploadFiles);
        return result;

    }

    private BooleanExpression isUser(Boolean isuser) {
        return isuser ? null : posts.isSecret.stringValue().eq(IsSecret.NORMAL.name());
    }

}
