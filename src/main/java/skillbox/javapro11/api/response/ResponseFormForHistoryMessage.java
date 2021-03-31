package skillbox.javapro11.api.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ResponseFormForHistoryMessage extends ResponseFormData {
    private List<PersonDTO> profiles;

    public ResponseFormForHistoryMessage(String error, LocalDateTime timestamp,
                                         ResponseData data, List<PersonDTO> profiles) {
        super(error, timestamp, data);
        this.profiles = profiles;
    }
}
