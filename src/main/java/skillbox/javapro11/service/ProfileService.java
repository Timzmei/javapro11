package skillbox.javapro11.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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

    private final PersonRepository personRepository;
    private final PostRepository postRepository;

    @Autowired
    public ProfileService (
        PersonRepository personRepository,
        PostRepository postRepository
    ) {
        this.personRepository = personRepository;
        this.postRepository = postRepository;
    }

    public Person getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) authentication.getPrincipal();
        return personRepository.findByEmail(person.getFirstName());
    }

    public Person editCurrentUser(@NotNull ProfileEditRequest profileEditRequest) {
        Person currentPerson = getCurrentUser();

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
        if (profileEditRequest.getTown() != null) {
            currentPerson.setCity(profileEditRequest.getTown());
        }
        if (profileEditRequest.getPermissionMessage() != null) {
            currentPerson.setPermissionMessage(profileEditRequest.getPermissionMessage());
        }

        personRepository.updatePerson(currentPerson);
        return currentPerson;
    }

    public void deleteCurrentUser() {
        Person person = getCurrentUser();
        personRepository.delete(person);
    }

    public Person findUserById(long id) {
        return personRepository.getOne(id);
    }

	public ResponseFormListData getUserWall(long userId, long offset, int itemPerPage) { //TODO Stub
        Person person = personRepository.findById(userId);
        Pageable pageable = getPageable(offset, itemPerPage);

        Page<Post> postPage = postRepository.findAllWherePerson(person, pageable);
        //build response
        return new ResponseFormListData(
                "string",
                LocalDateTime.now(),
                (int) postPage.getTotalElements(), //int ? total and offset
                (int) offset,
                itemPerPage,
                new ArrayList<>(getPostDTOListFromPostList(postPage.getContent()))
        );
	}

    public List<PostDTO> getPostDTOListFromPostList(List<Post> postList) {
        List<PostDTO> postDTOList = new ArrayList<>();
        postList.forEach(post -> postDTOList.add(getPostDTOFromPost(post)));
        return postDTOList;
    }

    public PostDTO getPostDTOFromPost(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTime(post.getTime());
        postDTO.setAuthor(getPersonDTOFromPerson(post.getPerson()));
        postDTO.setTitle(post.getTitle());
        postDTO.setPostText(post.getText());
        postDTO.setBlocked(post.isBlocked());
        postDTO.setLikes(post.getPostLikeList().size());
        postDTO.setComments(getCommentDTOListFromCommentList(post.getComments()));
        postDTO.setType(post.getTime().isBefore(LocalDateTime.now()) ? "POSTED" : "QUEUED"); //Enum?
        return postDTO;
    }

    public PersonDTO getPersonDTOFromPerson(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.setBirthDate(person.getBirthday());
        personDTO.setEmail(person.getEmail());
        personDTO.setPhone(person.getPhone());
        personDTO.setPhoto(person.getPhoto());
        personDTO.setAbout(person.getAbout());
        personDTO.setCity(); // it must be String
        personDTO.setCountry(); //it must be String
        personDTO.setMessagesPermission(person.getPermissionMessage());
        personDTO.setLastOnlineTime(person.getLastTimeOnline()); //localTime? why not
        personDTO.setBlocked(person.isBlocked());
        personDTO.setToken(); //where from I have to take a token?
    }

    public List<CommentDTO> getCommentDTOListFromCommentList(List<Comment> commentList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentList.forEach(comment -> commentDTOList.add(getCommentDTOFromComment(comment)));
        return commentDTOList;
    }

    public CommentDTO getCommentDTOFromComment(Comment comment) {

    }

    public Object postOnUserWall(long userId, long publishDate, PostRequestBody postBody) { //TODO Stub
        Person author = personRepository.findById(userId);

        LocalDateTime publishLocalDateTime = longToLocalDateTime(publishDate);
        publishLocalDateTime = getCorrectPublishLocalDateTime(publishLocalDateTime);

        Post post = new Post();
        post.setPerson(author);
        post.setTime(publishLocalDateTime);
        post.setTitle(postBody.getTitle());
        post.setText(postBody.getText);
        post.setBlocked(false);

        postRepository.save(post);

        //build response
        return null;
    }

    public Object searchUser(
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

        //TODO Mapping
        return null;
    }

    public Object blockUser(boolean isBlocked, long userId) {
        Person person = personRepository.findById(userId);
        person.setBlocked(isBlocked);
        personRepository.save(person);
        return null;
    }

    public LocalDateTime longToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public Pageable getPageable(long offset, int itemPerPage) {
        //itemPerPage can't be equal 0, cause we'll use it like divisor
        //I don't know why it may be equals 0, but anyway we are ready for this!
        itemPerPage = itemPerPage == 0 ? 1 : itemPerPage;
        int page =  (int) (offset / itemPerPage);
        return PageRequest.of(page, itemPerPage);
    }

    public LocalDateTime getCorrectPublishLocalDateTime(LocalDateTime publishLocalDateTime) {
        return publishLocalDateTime.isBefore(LocalDateTime.now()) ? LocalDateTime.now() : publishLocalDateTime;
    }
}
