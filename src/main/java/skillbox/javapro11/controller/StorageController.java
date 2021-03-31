package skillbox.javapro11.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skillbox.javapro11.api.request.RegisterRequest;

/**
 * Created by timur_guliev on 31.03.2021.
 */
@RestController
public class StorageController {

    @PostMapping("/api/v1/storage")
    public ResponseEntity storageFile(){
        return null;
    }

}
