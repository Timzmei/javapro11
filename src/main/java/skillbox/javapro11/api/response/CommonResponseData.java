package skillbox.javapro11.api.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseData extends CommonResponse {
    private ResponseData data;

    public CommonResponseData(ResponseData data, String errorMassage){
        super(errorMassage);
        this.data = data;
    }
}
