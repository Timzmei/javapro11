package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusResponse extends ResponseData {

    private boolean online;

    @JsonProperty(value = "last_activity")
    private LocalDateTime lastActivity;
}
