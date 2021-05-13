package skillbox.javapro11.service;

import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.enums.NotificationTypeCode;
import skillbox.javapro11.model.entity.Person;


public interface AccountService {

    String saveNotificationSetting(NotificationTypeCode notificationTypeCode, Boolean enable);

    Person getCurrentPerson();

    String getMailByToken(String token);

    Person findPersonByEmail(String email);

    Person addNewPerson(RegisterRequest registerRequest);

    boolean sendEmailToPerson(String email);

    String changePersonPassword(String token, String password);

    String changePersonEmail(String email);

    String registerNewUser(RegisterRequest registerRequest);
}
