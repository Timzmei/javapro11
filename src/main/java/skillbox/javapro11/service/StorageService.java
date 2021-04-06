package skillbox.javapro11.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.UploadImageResponse;
import skillbox.javapro11.api.response.UserStatusResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by timur_guliev on 03.04.2021.
 */
@Service
public class StorageService {

    @Value("${cloudinary.url}")
    String cloudinaryUrl;

    @Autowired
    private AccountService accountService;

    public CommonResponseData uploadImage(MultipartFile file, String type) {
        String error = "";
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        Map uploadResult = null;
        UploadImageResponse uploadImageResponse = new UploadImageResponse();

        try {
            File uploadedFile = convertMultiPartToFile(file);
            uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadImageResponse = getUploadImageResponse(uploadResult, type);
        } catch (IOException e) {
            error = e.getMessage();
        }

        CommonResponseData commonResponseData = new CommonResponseData(
                error,
                LocalDateTime.now(),
                uploadImageResponse
        );


        return commonResponseData;
    }

    private UploadImageResponse getUploadImageResponse(Map uploadResult, String path) {
//        Long currentUser = accountService.getCurrentPerson().getId();

        System.out.println((String) uploadResult.get("asset_id"));
        System.out.println((String) uploadResult.get("original_filename"));
        System.out.println((String) uploadResult.get("url"));
        System.out.println((String) uploadResult.get("format"));
        System.out.println(uploadResult.get("bytes"));
        System.out.println((String) uploadResult.get("resource_type"));
        System.out.println(uploadResult.get("created_at"));

        UploadImageResponse uploadImageResponse = new UploadImageResponse();
        uploadImageResponse.setId((String) uploadResult.get("asset_id"));
//        uploadImageResponse.setOwnerId(currentUser);
        uploadImageResponse.setFileName((String) uploadResult.get("original_filename"));
        uploadImageResponse.setRelativeFilePath((String) uploadResult.get("url"));
        uploadImageResponse.setRawFileURL(path);
        uploadImageResponse.setFileFormat((String) uploadResult.get("format"));
        uploadImageResponse.setBytes((Long) uploadResult.get("bytes"));
        uploadImageResponse.setFileType((String) uploadResult.get("resource_type"));
        uploadImageResponse.setCreatedAt((LocalDateTime) uploadResult.get("created_at"));

        return uploadImageResponse;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
