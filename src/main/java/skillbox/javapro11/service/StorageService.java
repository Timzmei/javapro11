package skillbox.javapro11.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.UploadImageResponse;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * Created by timur_guliev on 03.04.2021.
 */
@Service
public class StorageService {

    @Value("${cloudinary.url}")
    String cloudinaryUrl;

    private final AccountService accountService;
    private final PersonRepository personRepository;

    @Autowired
    public StorageService(AccountService accountService, PersonRepository personRepository) {
        this.accountService = accountService;
        this.personRepository = personRepository;
    }

    public CommonResponseData uploadImage(MultipartFile file) {
        String error = "";
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        Map uploadResult;
        UploadImageResponse uploadImageResponse = new UploadImageResponse();

        try {
            Person currentPerson = accountService.getCurrentPerson();
            File uploadedFile = convertMultiPartToFile(file);
            uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadImageResponse = getUploadImageResponse(uploadResult, currentPerson);
            currentPerson.setPhoto(uploadImageResponse.getRelativeFilePath());
            personRepository.updatePerson(currentPerson);
        } catch (IOException e) {
            error = e.getMessage();
        }

        return new CommonResponseData(
                error,
                LocalDateTime.now(),
                uploadImageResponse
        );
    }

    private UploadImageResponse getUploadImageResponse(Map uploadResult, Person person) {
        UploadImageResponse uploadImageResponse = new UploadImageResponse();
        uploadImageResponse.setId((String) uploadResult.get("asset_id"));
        uploadImageResponse.setOwnerId(person.getId());
        uploadImageResponse.setFileName((String) uploadResult.get("original_filename"));
        uploadImageResponse.setRelativeFilePath((String) uploadResult.get("url"));
        uploadImageResponse.setFileFormat((String) uploadResult.get("format"));
        uploadImageResponse.setBytes((Long) uploadResult.get("bytes"));
        uploadImageResponse.setFileType((String) uploadResult.get("resource_type"));
        uploadImageResponse.setCreatedAt((LocalDateTime) uploadResult.get("created_at"));

        return uploadImageResponse;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
