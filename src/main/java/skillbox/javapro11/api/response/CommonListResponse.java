package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skillbox.javapro11.service.ConvertLocalDateService;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonListResponse extends CommonResponse {
    private long total;
    private long offset;
    private int perPage;
    private List<ResponseData> data;


    public CommonListResponse(String error, LocalDateTime timestamp, List<ResponseData> data) {
        this.setError(error);
        this.setTimestamp(ConvertLocalDateService.convertLocalDateTimeToLong(timestamp));
        this.data = data;
    }

    public CommonListResponse(String error, LocalDateTime timestamp, long total, long offset, int perPage,
                              List<ResponseData> data) {
        this.setError(error);
        this.setTimestamp(ConvertLocalDateService.convertLocalDateTimeToLong(timestamp));
        this.total = total;
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }
}
