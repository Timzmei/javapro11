package skillbox.javapro11.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;

@Service
public class PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    public Person findPersonByEmail(String email) {
        LOGGER.info("findPersonByEmail " + email);
        return personRepository.findByEmail(email);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person add(RegisterRequest registerRequest) {
        Person newPerson = new Person(registerRequest);
        LOGGER.info("new Person in DB: " + registerRequest.toString());
        return personRepository.save(newPerson);
    }

    public String changePassword(String email, String password) {
        String message = ""; // for checking error if necessary
        Person curPerson = findPersonByEmail(email);
        curPerson.setPassword(password);
        save(curPerson);
        return message;
    }

    public String changeEmail(String email) {
        String message = "";// for checking error if necessary
        Person curPerson = findPersonByEmail(email);
        curPerson.setEmail(email);
        save(curPerson);
        return message;
    }
}
