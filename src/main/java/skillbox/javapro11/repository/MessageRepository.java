package skillbox.javapro11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skillbox.javapro11.model.entity.Message;

/**
 * Created by timurg on 05.05.2021.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findById(long id);
}
