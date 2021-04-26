package skillbox.javapro11.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.response.*;
import skillbox.javapro11.api.request.CommentRequest;
import skillbox.javapro11.model.entity.Comment;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.CommentRepository;
import skillbox.javapro11.repository.PostRepository;
import skillbox.javapro11.service.AccountService;
import skillbox.javapro11.service.PostService;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final AccountService accountService;

  @Override
  public CommonResponseData getPostSearch(String text, String author, long dateFrom, long dateTo, String tagsRequest,
                                          long offset, int limit) {
    CommonResponseData response = new CommonResponseData();
    List<String> tags = Arrays.asList(tagsRequest.split(","));
    List<Post> result = tagsRequest.length() > 0 && tags.size() != 0
        ? postRepository.searchPostTag(text, new Date(dateFrom), new Date(dateTo), author, tags)
        : postRepository.searchPost(text, new Date(dateFrom), new Date(dateTo), author);
    PostResponse.fromPostList(result);
    return response;
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
    if (post.getId() != person.getId()) {
      response.setError("You have no rights");
      response.setTimestamp(LocalDateTime.now());
    }
    post.setText(postRequest.getText());
    post.setTitle(postRequest.getTitle());
    return response;
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
    response.setData(PostResponse.fromPost(post));
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

  @Override
  public CommonListResponse getComments(long postId, int limit, int offset) {
//    List<Comment> comments = commentRepository.getComments(postId, limit, offset);
    if (!postRepository.findById(postId).isPresent()) {
      return new CommonListResponse("Пост не найден", LocalDateTime.now(), null);
    }
    Pageable pageable = getPageable(offset, limit, Sort.by(Sort.DEFAULT_DIRECTION, "time"));
    Page<Comment> commentPage = commentRepository.findAllByPostId(postId, pageable);


    return new CommonListResponse("", LocalDateTime.now(), commentPage.getTotalElements(),
        offset, limit, new ArrayList<>(CommentResponse.fromCommentList(commentPage.getContent())));
  }

  public CommonResponseData editedComment(long postId, long idComment, CommentRequest comment) {
    Person person = accountService.getCurrentPerson();
    Optional<Post> postOptional = postRepository.findById(postId);
    if (!postOptional.isPresent()) {
      return new CommonResponseData(null, "Пост не найден");
    }
    Post post = postOptional.get();
    Comment newComment;
    if (idComment != 0) {
      newComment = commentRepository.getByIdAndPostId(idComment, postId);
      if (comment == null) {
        return new CommonResponseData(null, "Комментарий не найден");
      }
      if (newComment.getAuthorId() != person.getId()) {
        return new CommonResponseData(null, "У вас нет прав");
      }
    } else {
      newComment = new Comment();
      newComment.setPost(post);
      newComment.setTime(LocalDateTime.now());
      newComment.setAuthorId(person.getId());
      if (comment.getParentId() != 0) {
        if (!commentRepository.findById(comment.getParentId()).isPresent()) {
          return new CommonResponseData(null, "Комментарий не найден");
        }
        newComment.setParentId(comment.getParentId());
      }
    }

    newComment.setCommentText(comment.getCommentText());

    commentRepository.save(newComment);

//    CommentResponse commentResponse = modelMapper.map(newComment, CommentResponse.class);
    return new CommonResponseData(CommentResponse.fromComment(newComment), "");
  }

  public CommonResponseData deleteComment(long postId, long idComment) {
    Person person = accountService.getCurrentPerson();
    Comment comment = commentRepository.getByIdAndPostId(idComment, postId);
    if (comment == null) {
      return new CommonResponseData(null, "Комментарий не найден");
    }
    if (comment.getAuthorId() != person.getId()) {
      return new CommonResponseData(null, "У вас нет прав");
    }

    comment.setDeleted(true);
    commentRepository.save(comment);

    return new CommonResponseData(CommentResponse.builder().id(comment.getId()).build(), "");
  }

  @Override
  public CommonResponseData reportComment(long postId, long idComment) {
    Comment comment = commentRepository.getByIdAndPostId(idComment, postId);
    if (comment == null) {
      return new CommonResponseData(null, "Комментарий не найден");
    }
    comment.setBlocked(true);
    commentRepository.save(comment);
    return new CommonResponseData(new StatusMessageResponse("ok"), "");
  }

  @Override
  public CommonResponseData recoverComment(long postId, long idComment) {
    CommonResponseData response = new CommonResponseData();
    Comment comment = commentRepository.getByIdAndPostId(idComment, postId);
    if (comment == null) {
      return new CommonResponseData(null, "Комментарий не найден");
    }
    comment.setDeleted(false);
    commentRepository.save(comment);
//    response.setData(modelMapper.map(comment, CommentResponse.class));
    response.setData(CommentResponse.fromComment(comment));
    return response;
  }

  public Pageable getPageable(long offset, int itemPerPage, Sort sort) {
    //TODO можте сделать общий utils
    itemPerPage = itemPerPage == 0 ? 1 : itemPerPage;
    int page = (int) (offset / itemPerPage);
    return PageRequest.of(page, itemPerPage, sort);
  }
}
