package skillbox.javapro11.service.post;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.CommentRequest;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.model.entity.Comment;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.CommentRepository;
import skillbox.javapro11.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final ModelMapper modelMapper;

  @Override
  public CommentResponse getComments(int postId, int limit, int offset) {
    return null;
  }

  public CommonResponseData editedComment(long postId, CommentRequest comment, Person person) throws NotFoundException {
    //TODO разобраться с ошибкой
    // разобраться с мапером
    Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Пост не найден"));
    Comment comment1;
    Comment newComment = new Comment();
    if (comment.getParentId() != 0) {
      comment1 = commentRepository.findById(comment.getParentId()).orElseThrow(() -> new NotFoundException("Комментарий не найден"));
      newComment.setParentId(comment.getParentId());
    }
    newComment.setPost(post);
    newComment.setCommentText(comment.getCommentText());
    newComment.setTime(LocalDateTime.now());
    newComment.setAuthorId(person.getId());

    CommentResponse commentResponse = modelMapper.map(newComment, CommentResponse.class);
    CommonResponseData response = CommonResponseData.builder().data(commentResponse).build();

    return response;
  }
}
