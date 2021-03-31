package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDTO implements ResponseData {

    private long id;

    private LocalDateTime time;

    private PersonDTO author;

    private String title;

    @JsonProperty(value = "post_text")
    private String postText;

    @JsonProperty(value = "is_blocked")
    private boolean isBlocked;

    private int likes;

    private List<CommentDTO> comments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
}
