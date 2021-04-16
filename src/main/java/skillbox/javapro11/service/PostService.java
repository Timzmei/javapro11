package skillbox.javapro11.service;

import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.PostResponse;

public interface PostService {
  CommentResponse getComments(int postId, int limit, int offset);
  PostResponse getPostSearch(int limit, long offset, String query);
  PostResponse getPostByID(long postId);
  PostResponse editPostById(long postId, long publishData, long personId);
  CommonResponseData deletePostById(long postId);
  PostResponse recoverPostById(long postId);
  PostResponse reportPost(long postId);
}
