package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDTO implements ResponseData {
    private long id;
    private String tag;
}
