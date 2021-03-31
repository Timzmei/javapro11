package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListLikeDTO implements ResponseData {

    private int likes;

    @JsonProperty(value = "users")
    private List<Long> usersId;
}
