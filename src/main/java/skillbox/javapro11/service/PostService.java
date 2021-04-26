package skillbox.javapro11.service;

import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.api.response.CommonResponseData;

public interface PostService {
  CommentResponse getComments(int postId, int limit, int offset);
  CommonResponseData getPostSearch(String text, String author, long dateFrom, long dateTo, String tags,
                                long offset, int limit);
  CommonResponseData getPostByID(long postId);
  CommonResponseData editPostById(long postId, long publishData, PostRequest postRequest);
  CommonResponseData deletePostById(long postId);
  CommonResponseData recoverPostById(long postId);
  CommonResponseData reportPost(long postId);
}
