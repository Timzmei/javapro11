package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListMessageDTO implements ResponseData {
    private long count;
    private List<MessageDTO> messages;
}
