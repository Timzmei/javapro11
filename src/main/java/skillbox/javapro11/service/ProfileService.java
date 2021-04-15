package skillbox.javapro11.service;

import com.sun.istack.NotNull;
import org.springframework.data.domain.Pageable;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.api.response.*;
import skillbox.javapro11.model.entity.Comment;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface ProfileService {

    PersonResponse getCurrentUser();

    PersonResponse editCurrentUser(@NotNull ProfileEditRequest profileEditRequest);

    CommonResponseData deleteCurrentUser();

    PersonResponse findUserById(long id);

    CommonListResponse getUserWall(long userId, long offset, int itemPerPage);

    CommonResponseData postOnUserWall(long userId, long publishDate, PostRequest postBody);

    CommonListResponse searchUser(
            String firstName,
            String lastName,
            Integer ageFrom,
            Integer ageTo,
            String country,
            String city,
            long offset,
            int itemPerPage
    );

    CommonResponseData blockUser(boolean isBlocked, long userId);

    LocalDateTime getLocalDateTimeFromLong(long timestamp);

    Pageable getPageable(long offset, int itemPerPage);

    LocalDateTime getCorrectPublishLocalDateTime(LocalDateTime publishLocalDateTime);

    List<PersonResponse> getPersonResponseListFromPersonList(List<Person> personList);

    PersonResponse getPersonResponseFromPerson(Person person);

    List<PostResponse> getPostResponseListFromPostList(List<Post> postList);

    PostResponse getPostResponseFromPost(Post post);

    List<CommentResponse> getCommentResponseListFromCommentList(List<Comment> commentList);

    CommentResponse getCommentResponseFromComment(Comment comment);

}
