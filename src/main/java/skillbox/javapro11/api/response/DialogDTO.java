package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DialogDTO implements ResponseData {

    private long id;

    @JsonProperty(value = "unread_count")
    private int unreadCount;

    @JsonProperty(value = "last_message")
    private MessageDTO lastMessage;
}
