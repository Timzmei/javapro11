package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse extends ResponseData {
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
