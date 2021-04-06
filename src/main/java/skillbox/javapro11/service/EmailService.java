package skillbox.javapro11.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skillbox.javapro11.controller.AccountController;
import skillbox.javapro11.model.entity.Person;

@Service
public class EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private PersonService personService;

    public boolean sendMessage(String email) {
        if (email.isBlank()){
            LOGGER.info("email is blank");
            return false;
        }
        Person personFromDB = personService.findPersonByEmail(email);
        if (personFromDB == null){
            LOGGER.info("Person with email " + email + " not found");
            return false;
        }

        //sendMail();
        return true;
    }
}
