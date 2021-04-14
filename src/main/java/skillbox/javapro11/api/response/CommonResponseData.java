package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
=======

import java.time.LocalDateTime;
>>>>>>> 8d59f25e52791733861134dcfd648ddf89eb2d54

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseData extends CommonResponse {
    private ResponseData data;

    public CommonResponseData(String error, LocalDateTime now, UploadImageResponse uploadImageResponse) {
    }
}
