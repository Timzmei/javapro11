package skillbox.javapro11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.javapro11.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
