package skillbox.javapro11.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skillbox.javapro11.model.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    public static List<PostResponse> fromPostList(List<Post> postList) {
        List<PostResponse> postResponseList = new ArrayList<>();
        postList.forEach(post -> postResponseList.add(fromPost(post)));
        return postResponseList;
    }

    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTime(),
                PersonResponse.fromPerson(post.getPerson()),
                post.getTitle(),
                post.getText(),
                post.isBlocked(),
                post.getPostLikeList().size(),
                CommentResponse.fromCommentList(post.getComments()),
                post.getTime().isBefore(LocalDateTime.now()) ? PostType.POSTED : PostType.QUEUED
        );
    }
}
