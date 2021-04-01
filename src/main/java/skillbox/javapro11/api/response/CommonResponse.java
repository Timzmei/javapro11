package skillbox.javapro11.api.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    private String error;
    private LocalDateTime timestamp;
}
