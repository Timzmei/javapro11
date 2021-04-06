package skillbox.javapro11.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.repository.CommentRepository;
import skillbox.javapro11.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  @Override
  public CommentResponse getComments(int postId, int limit, int offset) {
    return null;
  }
}
