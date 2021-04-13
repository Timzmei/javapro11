package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse extends ResponseData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value="parent_id")
    private Long parentId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value="comment_text")
    private String commentText;

    private long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value="post_id")
    private Long postId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime time;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value="author_id")
    private long authorId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value="is_blocked")
    private boolean isBlocked;
}
