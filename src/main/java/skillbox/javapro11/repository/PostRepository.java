package skillbox.javapro11.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;

import java.time.LocalDateTime;

/**
 * Created by Sizenko Egor on 30.03.2021.
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findAllByPerson(Person person, Pageable pageable);

	@Query(value = "SELECT tp1.*\n" +
			"FROM post tp1\n" +
			"left JOIN (select p.id, (p.first_name || p.last_name) fio from person p) t1 \n" +
			"WHERE lower(tp1.post_text) LIKE lower('%?2%') \n" +
			" and tp1.time between ?4 and ?5\n" +
			" and lower(t1.fio) like lower('%?3%')" +
			" and is_deleted is null", nativeQuery = true)
	Page<Post> findAllPostsBySearch(Pageable page, String text, LocalDateTime dateFrom,
									LocalDateTime dateTo);
}
