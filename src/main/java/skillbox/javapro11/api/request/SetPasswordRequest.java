package skillbox.javapro11.api.request;

import lombok.Data;

@Data
public class SetPasswordRequest {
    private String token;
    private String password;
}
