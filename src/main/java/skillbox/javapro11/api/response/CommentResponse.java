package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skillbox.javapro11.model.entity.Comment;
import skillbox.javapro11.repository.util.Utils;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse extends ResponseData {
    @JsonProperty(value="parent_id")
    private Long parentId;

    @JsonProperty(value="comment_text")
    private String commentText;

    private long id;

    @JsonProperty(value="post_id")
    private Long postId;

    private Long time;

    @JsonProperty(value="author_id")
    private long authorId;

    @JsonProperty(value="is_blocked")
    private boolean isBlocked;


    public static List<CommentResponse> fromCommentList(List<Comment> commentList) {
        List<CommentResponse> commentDTOList = new ArrayList<>();
        commentList.forEach(comment -> commentDTOList.add(fromComment(comment)));
        return commentDTOList;
    }

    public static CommentResponse fromComment(Comment comment) {
        return new CommentResponse(
                comment.getParentId(),
                comment.getCommentText(),
                comment.getId(),
                comment.getPost().getId(),
                Utils.getLongFromLocalDateTime(comment.getTime()),
                comment.getAuthorId(),
                comment.isBlocked()
        );
    }
}
