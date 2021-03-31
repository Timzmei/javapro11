package skillbox.javapro11.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.enums.NotificationTypeCode;
import skillbox.javapro11.model.entity.Person;

@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;

    public String saveNotificationSetting(NotificationTypeCode notificationTypeCode, Boolean enable) {
        String message = "";
        Person curPerson = getCurrentPerson();
        // пока нет данных о notifications
//        notificationSettingService.save(new NotificationSetting(notificationTypeCode, enable, curPerson));
        return message;
    }

    private Person getCurrentPerson() {
        return null;
    }

    public Person findPersonByEmail(String email) {
        return personService.findPersonByEmail(email);
    }

    public Person addNewPerson(RegisterRequest registerRequest) {
        return personService.add(registerRequest);
    }

    public boolean sendEmailToPerson(String email) {
        return emailService.sendMessage(email);
    }

    public String changePersonPassword(String token, String password) {
        return personService.changePassword(token, password);
    }

    public String changePersonEmail(String email) {
        return personService.changeEmail(email);
    }
}
