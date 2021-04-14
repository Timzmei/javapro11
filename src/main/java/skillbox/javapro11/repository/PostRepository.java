package skillbox.javapro11.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;

<<<<<<< HEAD
import java.time.LocalDateTime;
import java.util.Optional;

=======
/**
 * Created by Sizenko Egor on 30.03.2021.
 */

@Repository
>>>>>>> 8d59f25e52791733861134dcfd648ddf89eb2d54
public interface PostRepository extends JpaRepository<Post, Long> {

	Page<Post> findAllByPerson(Person person, Pageable pageable);}
