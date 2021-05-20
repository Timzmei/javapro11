package skillbox.javapro11.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillbox.javapro11.model.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  Comment getByIdAndPostId(long commentId, long postId);
  Page<Comment> findAllByPostIdAndDeletedFalse(long postId, Pageable pageable);
}
