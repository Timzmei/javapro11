package skillbox.javapro11.service.post;

import skillbox.javapro11.api.response.CommentResponse;

public interface PostService {
  CommentResponse getComments(int postId, int limit, int offset);

}
