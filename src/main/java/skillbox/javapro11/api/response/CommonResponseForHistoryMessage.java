package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class CommonResponseForHistoryMessage extends CommonResponseData {
    private List<PersonResponse> profiles;
}
