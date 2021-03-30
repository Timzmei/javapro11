package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserStatusDTO implements ResponseData {

    private boolean online;

    @JsonProperty(value = "last_activity")
    private LocalDateTime lastActivity;
}
