package skillbox.javapro11.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.enums.NotificationTypeCode;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.security.jwt.JwtTokenProvider;
import skillbox.javapro11.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private PersonServiceImpl personServiceImpl;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Override
    public String saveNotificationSetting(NotificationTypeCode notificationTypeCode, Boolean enable) {
        String message = "";
        Person curPerson = getCurrentPerson();
        // пока нет данных о notifications
//        notificationSettingService.save(new NotificationSetting(notificationTypeCode, enable, curPerson));
        return message;
    }

    @Override
    public Person getCurrentPerson() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        LOGGER.info("authorized user " + user.getUsername());
        return personServiceImpl.findPersonByEmail(user.getUsername());
    }

    @Override
    public String getMailByToken(String token) {
        return new JwtTokenProvider().getUserEmailFromToken(token);
    }

    @Override
    public Person findPersonByEmail(String email) {
        return personServiceImpl.findPersonByEmail(email);
    }

    @Override
    public Person addNewPerson(RegisterRequest registerRequest) {
        return personServiceImpl.add(registerRequest);
    }

    @Override
    public boolean sendEmailToPerson(String email) {
        return emailServiceImpl.sendMessage(email);
    }

    @Override
    public String changePersonPassword(String token, String password) {
        return personServiceImpl.changePassword(getMailByToken(token), password);
    }

    @Override
    public String changePersonEmail(String email) {
        return personServiceImpl.changeEmail(email);
    }
}
