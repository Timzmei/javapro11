package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseData extends CommonResponse {
    private ResponseData data;

    public CommonResponseData(String error, LocalDateTime now, UploadImageResponse uploadImageResponse) {
    }
}
