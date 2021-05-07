package skillbox.javapro11.service.impl;

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
import skillbox.javapro11.service.ConvertLocalDateService;
import skillbox.javapro11.service.StorageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by timur_guliev on 03.04.2021.
 */
@Service
public class StorageServiceImpl implements StorageService {

    @Value("${cloudinary.url}")
    String cloudinaryUrl;

    private final AccountServiceImpl accountServiceImpl;
    private final PersonRepository personRepository;

    @Autowired
    public StorageServiceImpl(AccountServiceImpl accountServiceImpl, PersonRepository personRepository) {
        this.accountServiceImpl = accountServiceImpl;
        this.personRepository = personRepository;
    }

    @Override
    public CommonResponseData uploadImage(MultipartFile file) {
        String error = "";
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);
        Map uploadResult;
        UploadImageResponse uploadImageResponse = new UploadImageResponse();

        try {
            Person currentPerson = accountServiceImpl.getCurrentPerson();
            File uploadedFile = convertMultiPartToFile(file);
            uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadImageResponse = getUploadImageResponse(uploadResult, currentPerson);
            currentPerson.setPhoto(uploadImageResponse.getRelativeFilePath());
            personRepository.save(currentPerson);
        } catch (IOException e) {
            error = e.getMessage();
        }

        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setError(error);
        commonResponseData.setTimestamp(ConvertLocalDateService.convertLocalDateTimeToLong(LocalDateTime.now()));
        commonResponseData.setData(uploadImageResponse);

        return commonResponseData;
    }

    private UploadImageResponse getUploadImageResponse(Map uploadResult, Person person) {
        UploadImageResponse uploadImageResponse = new UploadImageResponse();
        uploadImageResponse.setId((String) uploadResult.get("asset_id"));
        uploadImageResponse.setOwnerId(person.getId());
        uploadImageResponse.setFileName((String) uploadResult.get("original_filename"));
        uploadImageResponse.setRelativeFilePath((String) uploadResult.get("url"));
        uploadImageResponse.setFileFormat((String) uploadResult.get("format"));
        uploadImageResponse.setBytes((Integer) uploadResult.get("bytes"));
        uploadImageResponse.setFileType((String) uploadResult.get("resource_type"));
        uploadImageResponse.setCreatedAt(getCreatedAt(uploadResult));
        return uploadImageResponse;
    }

    private LocalDateTime getCreatedAt(Map uploadResult) {
        return LocalDateTime.parse((String) uploadResult.get("created_at"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


}
