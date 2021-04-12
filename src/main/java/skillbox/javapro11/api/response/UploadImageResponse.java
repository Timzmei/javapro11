package skillbox.javapro11.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Created by timur_guliev on 03.04.2021.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UploadImageResponse extends ResponseData {

    private String id;
    private long ownerId;
    private String fileName;
    private String relativeFilePath;
    private String rawFileURL;
    private String fileFormat;
    private long bytes;
    private String fileType;
    private LocalDateTime createdAt;


}
