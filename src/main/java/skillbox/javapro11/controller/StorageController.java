package skillbox.javapro11.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import skillbox.javapro11.service.impl.StorageServiceImpl;

/**
 * Created by timur_guliev on 31.03.2021.
 */
@RestController
@Slf4j
public class StorageController {

    private final StorageServiceImpl storageServiceImpl;

    @Autowired
    public StorageController(StorageServiceImpl storageServiceImpl) {
        this.storageServiceImpl = storageServiceImpl;
    }

    @PostMapping("/storage")
    public ResponseEntity storageFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("type") String type){ //не совесм понятно где этот тайп используется

        log.trace("/api/v1/storage");
        return ResponseEntity.ok(storageServiceImpl.uploadImage(file));
    }

}
