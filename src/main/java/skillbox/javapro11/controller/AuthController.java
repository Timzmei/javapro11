package skillbox.javapro11.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.AuthRequest;
import skillbox.javapro11.api.response.PersonResponse;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.service.AccountService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<PersonResponse> authUser(@RequestBody AuthRequest authRequest) {
        LOGGER.trace("POST /api/v1/auth/login");
        LOGGER.trace("authRequest = " + authRequest.toString());
        
        //Should be changed. Check mail and password to get the Person
        Person curPerson =  accountService.findPersonByEmail(authRequest.getEmail());

        return new ResponseEntity<>(PersonResponse.fromPerson(curPerson), HttpStatus.OK);
    }
}
