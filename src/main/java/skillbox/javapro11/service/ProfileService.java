package skillbox.javapro11.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.api.response.*;
import skillbox.javapro11.model.entity.Comment;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.repository.PostRepository;
import skillbox.javapro11.repository.util.PersonSpecificationsBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    private final AccountService accountService;
    private final PersonRepository personRepository;
    private final PostRepository postRepository;

    @Autowired
    public ProfileService(
            AccountService accountService,
            PersonRepository personRepository,
            PostRepository postRepository
    ) {
        this.accountService = accountService;
        this.personRepository = personRepository;
        this.postRepository = postRepository;
    }

    public CommonResponseData getCurrentUser() {
        return new CommonResponseData(getPersonResponseFromPerson(accountService.getCurrentPerson()), "string");
    }

    public CommonResponseData editCurrentUser(@NotNull ProfileEditRequest profileEditRequest) {
        Person currentPerson = accountService.getCurrentPerson();

        if (profileEditRequest.getFirstName() != null) {
            currentPerson.setFirstName(profileEditRequest.getFirstName());
        }
        if (profileEditRequest.getLastName() != null) {
            currentPerson.setLastName(profileEditRequest.getLastName());
        }
        if (profileEditRequest.getBirthDate() != null) {
            currentPerson.setBirthday(profileEditRequest.getBirthDate());
        }
        if (profileEditRequest.getPhone() != null) {
            currentPerson.setPhone(profileEditRequest.getPhone());
        }

        if (profileEditRequest.getPhoto() != null) {
            currentPerson.setPhoto(profileEditRequest.getPhoto());
        }

        if (profileEditRequest.getAbout() != null) {
            currentPerson.setAbout(profileEditRequest.getAbout());
        }

        if (profileEditRequest.getTown() != null) {
            currentPerson.setCity(profileEditRequest.getTown());
        }

        if (profileEditRequest.getCountry() != null) {
            currentPerson.setCountry(profileEditRequest.getCountry());
        }

        if (profileEditRequest.getPermissionMessage() != null) {
            currentPerson.setPermissionMessage(profileEditRequest.getPermissionMessage());
        }

        personRepository.save(currentPerson);

        return new CommonResponseData(getPersonResponseFromPerson(currentPerson), "string");
    }

    public CommonResponseData deleteCurrentUser() {

        Person currentPerson = accountService.getCurrentPerson();

        personRepository.delete(currentPerson);

        CommonResponseData responseData = new CommonResponseData();
        responseData.setError("string");
        responseData.setTimestamp(LocalDateTime.now());
        responseData.setData(new StatusMessageResponse("ok"));
        return responseData;
    }

    public CommonResponseData findUserById(long id) {
        PersonResponse personResponse = getPersonResponseFromPerson(personRepository.getOne(id));
        return new CommonResponseData(personResponse, "string");
    }

    public CommonListResponse getUserWall(long userId, long offset, int itemPerPage) {
        Person person = personRepository.findById(userId);
        Pageable pageable = getPageable(offset, itemPerPage);

        Page<Post> postPage = postRepository.findAllByPerson(person, pageable);
        //build response
        return new CommonListResponse(
                "string",
                LocalDateTime.now(),
                postPage.getTotalElements(),
                offset,
                itemPerPage,
                new ArrayList<>(getPostResponseListFromPostList(postPage.getContent()))
        );
    }

    public CommonResponseData postOnUserWall(long userId, long publishDate, PostRequest postBody) {
        Person author = personRepository.findById(userId);

        LocalDateTime publishLocalDateTime = getLocalDateTimeFromLong(publishDate);
        publishLocalDateTime = getCorrectPublishLocalDateTime(publishLocalDateTime);

        Post post = new Post();
        post.setPerson(author);
        post.setTime(publishLocalDateTime);
        post.setTitle(postBody.getTitle());
        post.setText(postBody.getText());
        post.setBlocked(false);
        //do I need to initial fields 'comments' and 'postLikesList' if object was created by @NoArgsConstructor Lombok
        //Below I use this fields in mapping method - getPostResponseFromPost(Post p)

        postRepository.save(post);

        CommonResponseData response = new CommonResponseData();
        response.setError("string");
        response.setTimestamp(LocalDateTime.now());
        response.setData(getPostResponseFromPost(post));

        return response;
    }

    public CommonListResponse searchUser(
            String firstName,
            String lastName,
            Integer ageFrom,
            Integer ageTo,
            String country,
            String city,
            long offset,
            int itemPerPage
    ) {
        Pageable pageable = getPageable(offset, itemPerPage);

        PersonSpecificationsBuilder builder = new PersonSpecificationsBuilder();

        if (firstName != null && !"".equals(firstName)) {
            builder.with("firstName", ":", firstName);
        }
        if (lastName != null && !"".equals(lastName)) {
            builder.with("lastName", ":", lastName);
        }
        if (ageFrom != null) {
            builder.with("birthday", "<", LocalDate.now().minusYears(ageFrom));
        }
        if (ageTo != null) {
            builder.with("birthday", ">", LocalDate.now().minusYears(ageTo));
        }
        if (country != null && !"".equals(country)) {
            builder.with("country", ":", country);
        }
        if (city != null && !"".equals(city)) {
            builder.with("city", ":", city);
        }
        Specification<Person> spec = builder.build();

        Page<Person> personPage = personRepository.findAll(spec, pageable);

        return new CommonListResponse(
                "string",
                LocalDateTime.now(),
                personPage.getTotalElements(),
                offset,
                itemPerPage,
                new ArrayList<>(getPersonResponseListFromPersonList(personPage.getContent()))
        );
    }

    public CommonResponseData blockUser(boolean isBlocked, long userId) {
        Person person = personRepository.findById(userId);
        person.setBlocked(isBlocked);
        personRepository.save(person);

        CommonResponseData responseData = new CommonResponseData();
        responseData.setError("string");
        responseData.setTimestamp(LocalDateTime.now());
        responseData.setData(new StatusMessageResponse("ok"));
        return responseData;
    }

    //serve methods ===========================================================================

    public LocalDateTime getLocalDateTimeFromLong(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public Pageable getPageable(long offset, int itemPerPage) {
        //itemPerPage can't be equal 0, cause we'll use it like divisor
        //I don't know why it may be equals 0, but anyway we are ready for this!
        itemPerPage = itemPerPage == 0 ? 1 : itemPerPage;
        int page = (int) (offset / itemPerPage);
        return PageRequest.of(page, itemPerPage);
    }

    public LocalDateTime getCorrectPublishLocalDateTime(LocalDateTime publishLocalDateTime) {
        return publishLocalDateTime.isBefore(LocalDateTime.now()) ? LocalDateTime.now() : publishLocalDateTime;
    }

    //Mapping methods ==============================================================================

    public List<PersonResponse> getPersonResponseListFromPersonList(List<Person> personList) {
        List<PersonResponse> personResponseList = new ArrayList<>();
        personList.forEach(person -> personResponseList.add(getPersonResponseFromPerson(person)));
        return personResponseList;
    }

    public PersonResponse getPersonResponseFromPerson(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getRegistrationDate(),
                person.getBirthday(),
                person.getEmail(),
                person.getPhone(),
                person.getPhoto(),
                person.getAbout(),
                person.getCity(),
                person.getCountry(),
                person.getPermissionMessage(),
                person.getLastTimeOnline(),
                person.isBlocked(),
                null
        );
    }

    public List<PostResponse> getPostResponseListFromPostList(List<Post> postList) {
        List<PostResponse> postResponseList = new ArrayList<>();
        postList.forEach(post -> postResponseList.add(getPostResponseFromPost(post)));
        return postResponseList;
    }

    public PostResponse getPostResponseFromPost(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTime(post.getTime());
        postResponse.setAuthor(getPersonResponseFromPerson(post.getPerson()));
        postResponse.setTitle(post.getTitle());
        postResponse.setPostText(post.getText());
        postResponse.setBlocked(post.isBlocked());
        postResponse.setLikes(post.getPostLikeList().size());
        postResponse.setComments(getCommentResponseListFromCommentList(post.getComments()));
        postResponse.setType(post.getTime().isBefore(LocalDateTime.now()) ? PostType.POSTED : PostType.QUEUED);
        return postResponse;
    }

    public List<CommentResponse> getCommentResponseListFromCommentList(List<Comment> commentList) {
        List<CommentResponse> commentDTOList = new ArrayList<>();
        commentList.forEach(comment -> commentDTOList.add(getCommentResponseFromComment(comment)));
        return commentDTOList;
    }

    public CommentResponse getCommentResponseFromComment(Comment comment) {
        return new CommentResponse(
                comment.getParentId(),
                comment.getCommentText(),
                comment.getId(),
                comment.getPost().getId(),
                comment.getTime(),
                comment.getAuthorId(),
                comment.isBlocked()
        );
    }
}
