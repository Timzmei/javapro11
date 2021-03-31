package skillbox.javapro11.api.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseForm {
    private String error;
    private LocalDateTime timestamp;
}
