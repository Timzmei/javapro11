package skillbox.javapro11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.javapro11.model.entity.Post;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
}
