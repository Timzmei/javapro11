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
public class CommonResponse {
    protected String error;
    protected LocalDateTime timestamp;

    public CommonResponse(String errorMessage) {
        this.error = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}
