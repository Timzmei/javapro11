package skillbox.javapro11.service;

import skillbox.javapro11.api.request.CommentRequest;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.response.CommonListResponse;
import skillbox.javapro11.api.response.CommonResponseData;

public interface PostService {
  CommonResponseData getPostSearch(String text, String author, long dateFrom, long dateTo, String tags,
                                long offset, int limit);
  CommonResponseData getPostByID(long postId);
  CommonResponseData editPostById(long postId, long publishData, PostRequest postRequest);
  CommonResponseData deletePostById(long postId);
  CommonResponseData recoverPostById(long postId);
  CommonResponseData reportPost(long postId);

  CommonListResponse getComments(long postId, int limit, int offset);
  CommonResponseData editedComment(long postId, long idComment, CommentRequest comment);
  CommonResponseData deleteComment(long postId, long idComment);
  CommonResponseData reportComment(long postId, long idComment);
  CommonResponseData recoverComment(long postId, long idComment);

}
