package skillbox.javapro11.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import skillbox.javapro11.ServiceTestConfiguration;
import skillbox.javapro11.api.request.CommentRequest;
import skillbox.javapro11.api.response.CommentResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.StatusMessageResponse;
import skillbox.javapro11.enums.PermissionMessage;
import skillbox.javapro11.model.entity.Comment;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.CommentRepository;
import skillbox.javapro11.repository.PostRepository;
import skillbox.javapro11.repository.util.Utils;
import skillbox.javapro11.service.impl.AccountServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(classes = ServiceTestConfiguration.class)
class PostServiceTest {

  @Autowired
  private PostService postService;
  @MockBean
  private AccountServiceImpl accountService;
  @MockBean
  private PostRepository postRepository;
  @MockBean
  private CommentRepository commentRepository;

  @BeforeEach
  public void setUp() {
    Mockito.reset(accountService);

    Person currentPerson = new Person(
        2L,
        "Ivan",
        "Ivanov",
        LocalDateTime.now().minusDays(10), // registration date
        LocalDate.now(), // birth date
        "ivanov@mail.com",
        "+7(111)222-33-44",
        "password",
        "photo",
        "about",
        "Russia",
        "Moscow",
        true,
        PermissionMessage.ALL,
        LocalDateTime.now(), // last online time
        false);

    Mockito.when(accountService.getCurrentPerson()).thenReturn(currentPerson);
  }

  @Test
  @DisplayName("Getting all comment")
  void getComments() {
  }

  @Test
  @DisplayName("Edit comment")
  void editedComment() {
    String newText = "new comment text";
    long postId = 11;
    long commentId = 2;
    Person person = accountService.getCurrentPerson();
    Optional<Post> post = Optional.of(new Post(postId,
        LocalDateTime.now(),
        person,
        "post 1", "post text 1",
        false, false,
        new ArrayList<>(), new ArrayList<>()));

    Mockito.when(postRepository.findById(postId)).thenReturn(post);
    Comment commentModel = new Comment(commentId, 0L,
        "comment text", new Post(),
        LocalDateTime.now(), 2L,
        false, false);
    Mockito.when(commentRepository.getByIdAndPostId(commentId, postId)).thenReturn(commentModel);

    CommentRequest newComment = new CommentRequest();
    newComment.setParentId(0);
    newComment.setCommentText("new comment text");

    CommonResponseData response = postService.editedComment(postId, commentId, newComment);
    CommentResponse responseComment = (CommentResponse) response.getData();

    assertTrue(response.getError().isEmpty());
    assertEquals("check text", newText, responseComment.getCommentText());
  }

  @Test
  @DisplayName("Добавление нового коммента. Ошибка")
  void addComment1() {
    CommonResponseData responseData = new CommonResponseData(null, "Пост не найден");
    long postId = 123;
    CommentRequest newComment = new CommentRequest();
    newComment.setParentId(0);
    newComment.setCommentText("new comment 1");

    CommonResponseData response = postService.editedComment(postId, 0, newComment);
    assertEquals("check data", responseData.getData(), response.getData());
    assertEquals("check error", responseData.getError(), response.getError());
  }

  @Test
  @DisplayName("Добавление нового комментария, при условии, что пост существует")
  void addComment2() {
    long postId = 11;
    Person person = accountService.getCurrentPerson();
    Optional<Post> post = Optional.of(new Post(postId,
        LocalDateTime.now(),
        person,
        "post 1", "post text 1",
        false, false,
        new ArrayList<>(), new ArrayList<>()));

    Mockito.when(postRepository.findById(postId)).thenReturn(post);
    CommentResponse defaultComment = CommentResponse.builder()
        .parentId(0L)
        .commentText("new comment 2")
        .blocked(false)
        .postId(postId)
        .authorId(2L)
        .time(Utils.getTimestampFromLocalDateTime(LocalDateTime.now()))
        .build();

    CommonResponseData responseData = new CommonResponseData(defaultComment, "");

    CommentRequest newComment = new CommentRequest();
    newComment.setParentId(0);
    newComment.setCommentText("new comment 2");

    CommonResponseData response = postService.editedComment(postId, 0, newComment);
    CommentResponse responseComment = (CommentResponse) response.getData();

    assertEquals("check parent comment", defaultComment.getParentId(), responseComment.getParentId());
    assertEquals("check text comment", defaultComment.getCommentText(), responseComment.getCommentText());
    assertEquals("check author comment", defaultComment.getAuthorId(), responseComment.getAuthorId());
    assertEquals("check post comment", defaultComment.getPostId(), responseComment.getPostId());
    assertEquals("check error", responseData.getError(), response.getError());
  }

  @Test
  @DisplayName("Удаление коммментария. Ошибка")
  void deleteCommentError() {
    long postId = 11;
    long commentId = 988777;
    CommonResponseData responseData = postService.deleteComment(postId, commentId);
    assertNull(responseData.getData());
    assertEquals("error", "Комментарий не найден", responseData.getError());
  }

  @Test
  @DisplayName("Удаление коммментария. Успешно")
  void deleteCommentOk() {
    long postId = 11;
    long commentId = 2;
    Comment commentModel = new Comment(commentId, 0L,
        "comment text", new Post(),
        LocalDateTime.now(), 2L,
        false, false);
    Mockito.when(commentRepository.getByIdAndPostId(commentId, postId)).thenReturn(commentModel);

    CommonResponseData responseData = postService.deleteComment(postId, commentId);
    assertNotNull(responseData.getData());
    assertTrue(responseData.getError().isEmpty());
    assertEquals("check response", commentId, ((CommentResponse) responseData.getData()).getId());
  }

  @Test
  @DisplayName("Жалоба на коммент")
  void reportComment() {
    long postId = 11;
    long commentId = 2;
    Comment commentModel = new Comment(commentId, 0L,
        "comment text", new Post(),
        LocalDateTime.now(), 2L,
        false, false);
    Mockito.when(commentRepository.getByIdAndPostId(commentId, postId)).thenReturn(commentModel);
    CommonResponseData responseData = postService.reportComment(postId, commentId);

    assertTrue(commentModel.isBlocked());
    assertNotNull(responseData.getData());
    assertEquals("check response", "ok", ((StatusMessageResponse) responseData.getData()).getMessage());
    assertTrue(responseData.getError().isEmpty());
  }

  @Test
  @DisplayName("Восстановление коммента")
  void recoverComment() {
    long postId = 11;
    long commentId = 2;
    Comment commentModel = new Comment(commentId, 0L,
        "comment text", new Post(),
        LocalDateTime.now(), 2L,
        false, false);
    Mockito.when(commentRepository.getByIdAndPostId(commentId, postId)).thenReturn(commentModel);

    CommonResponseData responseData = postService.recoverComment(postId, commentId);

    assertFalse(commentModel.isDeleted());
    assertNotNull(responseData.getData());
    assertNull(responseData.getError());
  }
}