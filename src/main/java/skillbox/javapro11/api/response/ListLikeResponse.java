package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListLikeResponse extends ResponseData {

    private int likes;

    @JsonProperty(value = "users")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> usersId;
}
