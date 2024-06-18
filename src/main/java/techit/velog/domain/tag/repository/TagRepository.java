package techit.velog.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techit.velog.domain.tag.entity.Tags;

public interface TagRepository extends JpaRepository<Tags, Long> {
    boolean existsByName(String s);
}
