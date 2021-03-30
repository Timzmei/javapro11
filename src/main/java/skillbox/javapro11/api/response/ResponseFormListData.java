package skillbox.javapro11.api.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class ResponseFormListData extends ResponseForm {
    private int total;
    private int offset;
    private int perPage;
    private List<ResponseData> data;

    public ResponseFormListData(String error, LocalDateTime timestamp,
                                int total, int offset, int perPage,
                                List<ResponseData> data) {
        super(error, timestamp);
        this.total = total;
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
    }
}
