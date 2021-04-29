package skillbox.javapro11.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAllByPerson(Person person, Pageable pageable);
}
