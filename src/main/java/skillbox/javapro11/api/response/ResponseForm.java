package skillbox.javapro11.api.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseForm {
    private String error;
    private LocalDateTime timestamp;

    public ResponseForm(String errorMessage) {
        this.error = errorMessage;
        this.timestamp = LocalDateTime.now();
    }
}
