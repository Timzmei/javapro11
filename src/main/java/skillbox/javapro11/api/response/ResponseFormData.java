package skillbox.javapro11.api.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseFormData extends ResponseForm {
    private ResponseData data;

    public ResponseFormData(String error, LocalDateTime timestamp, ResponseData data) {
        super(error, timestamp);
        this.data = data;
    }
}
