package skillbox.javapro11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.PostLike;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

	@Query(value = "SELECT pl FROM PostLike pl WHERE pl.person=:person AND pl.post.id=:postId")
	PostLike findByPersonAndPost(Person person, long postId);

	@Query(value = "SELECT pl.person.id FROM PostLike pl WHERE pl.post.id=:postId")
	List<Long> getAllUsersIdWhiLikePost(long postId);

	@Modifying
	@Query(value = "DELETE FROM PostLike pl WHERE pl.post.id=:postId")
	void deleteByPostId(long postId);
}
