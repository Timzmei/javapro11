package skillbox.javapro11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.javapro11.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
