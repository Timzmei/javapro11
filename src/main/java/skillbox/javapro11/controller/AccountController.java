package skillbox.javapro11.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.NotificationsRequest;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.api.request.SetEmailRequest;
import skillbox.javapro11.api.request.SetPasswordRequest;
import skillbox.javapro11.api.response.CommonResponse;
import skillbox.javapro11.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> personRegister(@RequestBody RegisterRequest registerRequest){
        LOGGER.trace("/api/v1/account/register");

        String message = accountService.registerNewUser(registerRequest);
        return new ResponseEntity<>(new CommonResponse(message), HttpStatus.OK);
    }

    @PutMapping("/password/recovery")
    public ResponseEntity<CommonResponse> passwordRecovery(@RequestBody String email){
        LOGGER.trace("/api/v1/account/password/recovery");

        String message = "";
        boolean mailIsSend = accountService.sendEmailToPerson(email);
        if (!mailIsSend){
            message = String.format("Mail was not send to %s", email);
        }
        return new ResponseEntity<>(new CommonResponse(message), HttpStatus.OK);
    }

    @PutMapping("/password/set")
    public ResponseEntity<CommonResponse> passwordChange(@RequestBody SetPasswordRequest setPasswordRequest){
        LOGGER.trace("/api/v1/account/password/set");

        String message = accountService.changePersonPassword(setPasswordRequest.getToken(), setPasswordRequest.getPassword().getPassword());
        return new ResponseEntity<>(new CommonResponse(message), HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<CommonResponse> emailChange(@RequestBody SetEmailRequest email){
        LOGGER.trace("/api/v1/account/email");

        String message = accountService.changePersonEmail(email.getEmail());
        return new ResponseEntity<>(new CommonResponse(message), HttpStatus.OK);
    }

    @PutMapping("/notifications")
    public ResponseEntity<CommonResponse> accountNotifications(@RequestBody NotificationsRequest notificationsRequest){
        LOGGER.trace("/api/v1/account/notifications");
        String message = accountService
                .saveNotificationSetting(notificationsRequest.getNotificationTypeCode(),notificationsRequest.getEnable());
        return new ResponseEntity<>(new CommonResponse(message), HttpStatus.OK);
    }

}
