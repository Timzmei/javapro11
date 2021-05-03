package skillbox.javapro11.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.javapro11.model.entity.Tag;

/**
 * Created by Artem on 21.04.2021.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
  Tag findByTag(String tag);
  Page<Tag> findAllByTag(String tag, Pageable pageable);
}
