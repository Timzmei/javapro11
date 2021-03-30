package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDTO implements ResponseData {
    @JsonProperty(value="parent_id")
    private long parentId;

    @JsonProperty(value="comment_text")
    private String commentText;

    private long id;

    @JsonProperty(value="post_id")
    private String postId;

    private LocalDateTime time;

    @JsonProperty(value="author_id")
    private long authorId;

    @JsonProperty(value="is_blocked")
    private boolean isBlocked;
}
