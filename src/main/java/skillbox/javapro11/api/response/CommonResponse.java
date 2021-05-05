package skillbox.javapro11.api.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skillbox.javapro11.service.ConvertTimeService;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    protected String error;
    protected Long timestamp;

    public CommonResponse(String errorMessage) {
        this.error = errorMessage;
        this.timestamp = ConvertTimeService.convertLocalDateTimeToLong(LocalDateTime.now());
    }
}
