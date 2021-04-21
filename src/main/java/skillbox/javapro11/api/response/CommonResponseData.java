package skillbox.javapro11.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD

import java.time.LocalDateTime;
=======
>>>>>>> faa7197e94e2dba033117579396503fd06d68190

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseData extends CommonResponse {
    private ResponseData data;

    public CommonResponseData(ResponseData data, String errorMassage){
        super(errorMassage);
        this.data = data;
    }
}
