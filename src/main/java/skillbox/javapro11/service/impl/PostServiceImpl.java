package skillbox.javapro11.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.CommentRequest;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.StatusMessageResponse;
import skillbox.javapro11.model.entity.Comment;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.CommentRepository;
import skillbox.javapro11.repository.PostRepository;
import skillbox.javapro11.service.AccountService;
import skillbox.javapro11.service.PostService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final AccountService accountService;
  private final ModelMapper modelMapper;

  @Override
  public CommentResponse getComments(long postId, int limit, int offset) {
    return null;
  }

  @Override
  public CommonResponseData editedComment(long postId, long idComment, CommentRequest comment) {
    Person person = accountService.getCurrentPerson();
    CommonResponseData response = new CommonResponseData();
    Optional<Post> postOptional = postRepository.findById(postId);
    if (!postOptional.isPresent()) {
      response.setError("Пост не найден");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    Post post = postOptional.get();
    Comment newComment;
    if (idComment != 0) {
      Optional<Comment> commentOptional = commentRepository.findById(idComment);
      if (!commentOptional.isPresent()) {
        response.setError("Комментарий не найден");
        response.setTimestamp(LocalDateTime.now());
        return response;
      }
      newComment = commentOptional.get();
      if (newComment.getAuthorId() != person.getId()) {
        response.setError("У вас нет прав");
        response.setTimestamp(LocalDateTime.now());
        return response;
      }
    } else {
      newComment = new Comment();
      newComment.setPost(post);
      newComment.setTime(LocalDateTime.now());
      newComment.setAuthorId(person.getId());
      if (comment.getParentId() != 0) {
        if (!commentRepository.findById(comment.getParentId()).isPresent()) {
          response.setError("Комментарий не найден");
          response.setTimestamp(LocalDateTime.now());
          return response;
        }
        newComment.setParentId(comment.getParentId());
      }
    }

    newComment.setCommentText(comment.getCommentText());

    commentRepository.save(newComment);

    CommentResponse commentResponse = modelMapper.map(newComment, CommentResponse.class);
    response.setData(commentResponse);
    return response;
  }

  @Override
  public CommonResponseData deleteComment(long postId, long idComment) {
    Person person = accountService.getCurrentPerson();
    CommonResponseData response = new CommonResponseData();
    Optional<Comment> commentOptional = commentRepository.findById(idComment);
    if (!commentOptional.isPresent()) {
      response.setError("Комментарий не найден");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    Comment comment = commentOptional.get();
    if (comment.getAuthorId() != person.getId()) {
      response.setError("У вас нет прав");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
//    Optional<Post> postOptional = postRepository.findById(postId);
//    if (!postOptional.isPresent()) {
//      response.setError("Пост не найден");
//      response.setTimestamp(LocalDateTime.now());
//      return response;
//    }

    comment.setDeleted(true);
    commentRepository.save(comment);

    response.setData(CommentResponse.builder().id(comment.getId()).build());
    return response;
  }

  @Override
  public CommonResponseData reportComment(long postId, long idComment) {
    CommonResponseData response = new CommonResponseData();
    Optional<Comment> commentOptional = commentRepository.findById(idComment);
    if (!commentOptional.isPresent()) {
      response.setError("Комментарий не найден");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    Comment comment = commentOptional.get();
    Optional<Post> postOptional = postRepository.findById(postId);
    if (!postOptional.isPresent()) {
      response.setError("Пост не найден");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    comment.setBlocked(true);
    commentRepository.save(comment);
    response.setData(new StatusMessageResponse("ok"));
    return response;
  }

  @Override
  public CommonResponseData recoverComment(long postId, long idComment) {
    CommonResponseData response = new CommonResponseData();
    Optional<Comment> commentOptional = commentRepository.findById(idComment);
    if (!commentOptional.isPresent()) {
      response.setError("Комментарий не найден");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    Comment comment = commentOptional.get();
    Optional<Post> postOptional = postRepository.findById(postId);
    if (!postOptional.isPresent()) {
      response.setError("Пост не найден");
      response.setTimestamp(LocalDateTime.now());
      return response;
    }
    comment.setDeleted(false);
    commentRepository.save(comment);
    response.setData(modelMapper.map(comment, CommentResponse.class));
    return response;
  }
}
