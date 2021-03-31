package skillbox.javapro11.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.NotificationsRequest;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.api.request.SetPasswordRequest;
import skillbox.javapro11.api.response.ResponseForm;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<ResponseForm> personRegister(@RequestBody RegisterRequest registerRequest){
        LOGGER.trace("/api/v1/account/register");

        String email = registerRequest.getEmail();
        String message = "";
        Person curPerson =  accountService.findPersonByEmail(email);
        if (curPerson != null) {
            message = String.format("User with email %s already exists", email);
        }
        else{
            accountService.addNewPerson(registerRequest);
        }
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

    @PutMapping("/password/recovery")
    public ResponseEntity<ResponseForm> passwordRecovery(@RequestBody String email){
        LOGGER.trace("/api/v1/account/password/recovery");

        String message = "";
        boolean mailIsSend = accountService.sendEmailToPerson(email);
        if (!mailIsSend){
            message = String.format("Mail was not send to %s", email);
        }
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

    @PutMapping("/password/set")
    public ResponseEntity<ResponseForm> passwordChange(@RequestBody SetPasswordRequest setPasswordRequest){
        LOGGER.trace("/api/v1/account/password/set");

        String message = accountService.changePersonPassword(setPasswordRequest.getToken(), setPasswordRequest.getPassword());
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

    @PutMapping("/password/email")
    public ResponseEntity<ResponseForm> emailChange(@RequestBody String email){
        LOGGER.trace("/api/v1/account/password/email");

        String message = accountService.changePersonEmail(email);
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

    @PutMapping("/notifications")
    public ResponseEntity<ResponseForm> accountNotifications(@RequestBody NotificationsRequest notificationsRequest){
        LOGGER.trace("/api/v1/account/notifications");
        String message = accountService
                .saveNotificationSetting(notificationsRequest.getNotificationTypeCode(),notificationsRequest.getEnable());
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

}
