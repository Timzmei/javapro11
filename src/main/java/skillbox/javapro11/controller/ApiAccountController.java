package skillbox.javapro11.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.EmailRequest;
import skillbox.javapro11.api.request.NotificationsRequest;
import skillbox.javapro11.api.request.RecoveryRequest;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.api.request.SetPasswordRequest;

@RestController
@RequestMapping("/api/v1/account")
public class ApiAccountController {

    @PostMapping("/register")
    public ResponseEntity userRegister(@RequestBody RegisterRequest registerRequest){
        return null;
    }

    @PutMapping("/password/recovery")
    public ResponseEntity passwordRecovery(@RequestBody EmailRequest emailRequest){
        return null;
    }

    @PutMapping("/password/set")
    @PreAuthorize("")
    public ResponseEntity passwordChange(@RequestBody SetPasswordRequest setPasswordRequest){
        return null;
    }

    @PutMapping("/password/email")
    @PreAuthorize("")
    public ResponseEntity emailChange(@RequestBody EmailRequest emailRequest){
        return null;
    }

    @PutMapping("/notifications")
    @PreAuthorize("")
    public ResponseEntity accountNotifications(@RequestBody NotificationsRequest notificationsRequest){
        return null;
    }

}
