package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse extends ResponseData {

    private long id;

    private LocalDateTime time;

    private PersonResponse author;

    private String title;

    @JsonProperty(value = "post_text")
    private String postText;

    @JsonProperty(value = "is_blocked")
    private boolean isBlocked;

    private int likes;

    private List<CommentResponse> comments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostType type;
}
