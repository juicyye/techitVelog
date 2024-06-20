package techit.velog.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import techit.velog.domain.comment.dto.CommentRespDto;
import techit.velog.domain.comment.entity.Comment;
import techit.velog.domain.comment.entity.QComment;

import java.util.List;

import static techit.velog.domain.comment.dto.CommentRespDto.*;
import static techit.velog.domain.comment.entity.QComment.*;

public class CommentCustomRepositoryImpl implements CommentCustomRepository {
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    public CommentCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findAllByPost(Long postId) {
        return queryFactory.select(comment)
                .from(comment)
                .where(comment.posts.id.eq(postId))
                .orderBy(comment.parent.id.asc().nullsFirst())
                .fetch();
    }
}
