package skillbox.javapro11.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.NotificationsRequest;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.api.request.SetPasswordRequest;
import skillbox.javapro11.api.response.ResponseForm;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.service.AccountService;;

@RestController
@RequestMapping("/api/v1/account")
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
        if (curPerson != null)
        {
            message = String.format("User with email : %s already exists", email);
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
            message = String.format("Error by sending message to %s", email);;
        }
        String.format("Mail was not send to %s", email);
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

    @PutMapping("/password/set")
    @PreAuthorize("")
    public ResponseEntity<ResponseForm> passwordChange(@RequestBody SetPasswordRequest setPasswordRequest){
        LOGGER.trace("/api/v1/account/password/set");

        String message = accountService.changePersonPassword(setPasswordRequest.getToken(), setPasswordRequest.getPassword());
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

    @PutMapping("/password/email")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ResponseForm> emailChange(@RequestBody String email){
        LOGGER.trace("/api/v1/account/password/email");

        String message = accountService.changePersonEmail(email);
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

    @PutMapping("/notifications")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ResponseForm> accountNotifications(@RequestBody NotificationsRequest notificationsRequest){
        LOGGER.trace("/api/v1/account/notifications");
        String message = accountService
                .saveNotificationSetting(notificationsRequest.getNotificationTypeCode(),notificationsRequest.getEnable());
        return new ResponseEntity<>(new ResponseForm(message), HttpStatus.OK);
    }

}
