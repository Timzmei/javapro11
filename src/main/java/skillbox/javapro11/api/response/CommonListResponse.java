package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonListResponse extends CommonResponse {
    private long total;
    private long offset;
    private int perPage;
    private List<ResponseData> data;
}
