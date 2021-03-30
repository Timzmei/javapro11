package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountMessageDTO implements ResponseData{
    private int count;
}
