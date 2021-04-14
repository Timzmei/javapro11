package skillbox.javapro11.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.PostResponse;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.CommentRepository;
import skillbox.javapro11.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  @Override
  public CommentResponse getComments(int postId, int limit, int offset) {
    return null;
  }

  @Override
  public PostResponse getPostSearch(int limit, long offset, String query) {
    return null;
  }

  @Override
  public PostResponse getPostByID(long postId) {
    return null;
  }

  @Override
  public PostResponse editPostById(long postId, long publishData, long personId) {
    return null;
  }

  @Override
  public CommonResponseData deletePostById(long postId) {
    
    CommonResponseData response = new CommonResponseData();
    Optional<Post> optionalPost = postRepository.findById(postId);
    if (!optionalPost.isPresent()) {
      response.setError("Post id = " + postId + " not found.");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    Post post = optionalPost.get();
    post.setDeleted(true);
    postRepository.save(post);
    response.setData(PostResponse.builder().id(post.getId()).build());
    return response;
  }

  @Override
  public PostResponse recoverPostById(long id) {
    return null;
  }

  @Override
  public PostResponse reportPost(long id) {
    return null;
  }
}
