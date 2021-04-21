package skillbox.javapro11.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.PostResponse;
import skillbox.javapro11.api.response.StatusMessageResponse;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.CommentRepository;
import skillbox.javapro11.repository.PostRepository;
import skillbox.javapro11.service.AccountService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final AccountService accountService;

  @Override
  public CommentResponse getComments(int postId, int limit, int offset) {
    return null;
  }

  @Override
  public CommentResponse getPostSearch(String text, String author, long dateFrom, long dateTo, String tagsRequest,
                                       long offset, int limit, long personId) {
    CommonResponseData response = new CommonResponseData();
//    Optional<Post> postOptional = postRepository.
    return null;
  }

  @Override
  public CommonResponseData getPostByID(long postId) {
    Person person = accountService.getCurrentPerson();
    CommonResponseData response = new CommonResponseData();
    Optional<Post> optionalPost = postRepository.findById(postId);
    if (optionalPost.isEmpty()) {
      response.setError(person + "\nPost id = " + postId + " not found.");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    Post post = optionalPost.get();
    postRepository.save(post);
    response.setData(PostResponse.builder().id(post.getId()).build());
    return response;
  }

  @Override
  public CommonResponseData editPostById(long postId, long publishData, PostRequest postRequest) {
    Person person = accountService.getCurrentPerson();
    CommonResponseData response = new CommonResponseData();
    Optional<Post> optionalPost = postRepository.findById(postId);
    if (optionalPost.isEmpty()) {
      response.setError(person + "\nPost id = " + postId + " not found.");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }

    Post post = optionalPost.get();
    post.setText(postRequest.getText());
    post.setTitle(postRequest.getTitle());
    post.setTime(LocalDateTime.now());

    return null;
  }

  @Override
  public CommonResponseData deletePostById(long postId) {
    Person person = accountService.getCurrentPerson();
    CommonResponseData response = new CommonResponseData();
    Optional<Post> optionalPost = postRepository.findById(postId);
    if (optionalPost.isEmpty()) {
      response.setError(person + "\nPost id = " + postId + " not found.");
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
  public CommonResponseData recoverPostById(long postId) {
    CommonResponseData response = new CommonResponseData();
    Optional<Post> postOptional = postRepository.findById(postId);
    if (postOptional.isEmpty()) {
      response.setError("Post not found");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    Post post = postOptional.get();
    post.setDeleted(false);
    postRepository.save(post);
//    response.setData( );
    return response;
  }

  @Override
  public CommonResponseData reportPost(long postId) {
    CommonResponseData response = new CommonResponseData();
    Optional<Post> postOptional = postRepository.findById(postId);
    if (postOptional.isEmpty()) {
      response.setError("Post not found");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    Post post = postOptional.get();
    post.setBlocked(true);
    postRepository.save(post);
    response.setData(new StatusMessageResponse("OK"));
    return response;
  }
}
