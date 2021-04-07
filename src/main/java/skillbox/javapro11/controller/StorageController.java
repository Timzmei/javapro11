package skillbox.javapro11.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import skillbox.javapro11.service.StorageService;

/**
 * Created by timur_guliev on 31.03.2021.
 */
@RestController
@Slf4j
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/storage")
    public ResponseEntity storageFile(@PathVariable MultipartFile file){
        log.trace("/api/v1/storage");
        return ResponseEntity.ok(storageService.uploadImage(file));
    }

}
