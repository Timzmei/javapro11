package skillbox.javapro11.service;

import org.springframework.web.multipart.MultipartFile;
import skillbox.javapro11.api.response.CommonResponseData;

public interface StorageService {

    CommonResponseData uploadImage(MultipartFile file);
}
