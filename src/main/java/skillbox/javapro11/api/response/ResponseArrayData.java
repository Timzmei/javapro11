package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseArrayData {
    private List<ResponseData> data;
}
