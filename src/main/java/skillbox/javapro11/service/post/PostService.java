package skillbox.javapro11.service.post;

import skillbox.javapro11.api.request.CommentRequest;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.api.response.CommonResponseData;

public interface PostService {
  CommentResponse getComments(long postId, int limit, int offset);
  CommonResponseData editedComment(long postId, long idComment, CommentRequest comment);
  CommonResponseData deleteComment(long postId, long idComment);
}
