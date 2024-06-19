package techit.velog.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techit.velog.domain.tag.entity.Tags;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tags, Long>, TagRepositoryCustom {
    boolean existsByName(String s);

    Optional<Tags> findByName(String name);
}
