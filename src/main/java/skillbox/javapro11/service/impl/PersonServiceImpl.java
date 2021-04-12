package skillbox.javapro11.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findPersonByEmail(String email) {
        LOGGER.info("findPersonByEmail " + email);
        return personRepository.findByEmail(email);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person add(RegisterRequest registerRequest) {
        Person newPerson = new Person(registerRequest);
        LOGGER.info("new Person in DB: " + registerRequest.toString());
        return personRepository.save(newPerson);
    }

    @Override
    public String changePassword(String email, String password) {
        String message = ""; // for checking error if necessary
        Person curPerson = findPersonByEmail(email);
        curPerson.setPassword(password);
        save(curPerson);
        return message;
    }

    @Override
    public String changeEmail(String email) {
        String message = "";// for checking error if necessary
        Person curPerson = findPersonByEmail(email);
        curPerson.setEmail(email);
        save(curPerson);
        return message;
    }
}
