package skillbox.javapro11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import skillbox.javapro11.model.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    Person findByEmail(String email);

    Person findById(long id);

    Person findByPassword(String passwordNew);
}