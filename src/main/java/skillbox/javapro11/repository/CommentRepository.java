package skillbox.javapro11.repository;

import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.javapro11.model.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  Comment getByIdAndPostId(long commentId, long postId);

//  @Query(value = "select * \n" +
//      "from comment \n" +
//      "where is_deleted = false and post_id = ?1 \n" +
//      "order by time asc \n" +
//      "limit ?2 offset ?3", nativeQuery = true)
//  List<Comment> getComments(long postId, int limit, int offset);

  Page<Comment> findAllByPostId(long postId, Pageable pageable);
}
