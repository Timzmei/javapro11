package skillbox.javapro11.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.service.StorageService;

/**
 * Created by timur_guliev on 31.03.2021.
 */
@RestController
public class StorageController {

    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/api/v1/storage")
    public ResponseEntity storageFile(@PathVariable String type,
                                      @PathVariable MultipartFile file){
//        CommonResponseData commonResponseData = storageService.uploadImage(type);

        return ResponseEntity.ok(storageService.uploadImage(file, type));
    }

}
