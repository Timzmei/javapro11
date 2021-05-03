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
import skillbox.javapro11.enums.PermissionMessage;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
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
  }

  @Test
  @DisplayName("Add comment. Error: post not found")
  void addComment1() {
    CommonResponseData responseData = new CommonResponseData(null, "Пост не найден");
    long postId = 123;
    CommentRequest newComment = new CommentRequest();
    newComment.setParentId(0);
    newComment.setCommentText("new comment 1");

    CommonResponseData response = postService.editedComment(postId, 0, newComment);
    assertEquals("check data", responseData.getData(),response.getData());
    assertEquals("check error", responseData.getError(),response.getError());
  }
  
  @Test
  @DisplayName("Добавление нового комментария, при условии что пост существует")
  void addComment2() {
    long postId = 11;
    Person person = accountService.getCurrentPerson();
    Post postM = new Post();
//    postM.setBlocked(false);
//    postM.setDeleted(false);
//    postM.setText("post text 1");
//    postM.setTitle("post 1");
    postM.setPerson(person);
    postM.setTime(LocalDateTime.now());
//    Optional<Post> post = Optional.of(new Post(postId,
//        LocalDateTime.now(),
//        person,
//        "post 1", "post text 1",
//        false, false,
//        new ArrayList<>(), new ArrayList<>()));
    Optional<Post> post = Optional.of(postM);

    Mockito.when(postRepository.findById(postId)).thenReturn(post);
    CommentResponse defaultComment = CommentResponse.builder()
        .parentId(0L)
        .commentText("new comment 2")
        .isBlocked(false)
        .postId(1L)
        .authorId(1L)
        .time(Utils.getTimestampFromLocalDateTime(LocalDateTime.now()))
        .build();

    CommonResponseData responseData = new CommonResponseData(defaultComment, "");

    CommentRequest newComment = new CommentRequest();
    newComment.setParentId(0);
    newComment.setCommentText("new comment 2");

    CommonResponseData response = postService.editedComment(postId, 0, newComment);
//    assertEquals("check data", responseData.getData(),response.getData());
    System.out.println("data = " + response.getData());
//    assertEquals("check error", responseData.getError(),response.getError());
  }

  @Test
  @DisplayName("Delete comment")
  void deleteComment() {
  }

  @Test
  @DisplayName("Report comment")
  void reportComment() {
  }

  @Test
  @DisplayName("Recover comment")
  void recoverComment() {
  }
}