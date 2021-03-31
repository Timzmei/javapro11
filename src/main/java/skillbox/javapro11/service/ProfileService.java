package skillbox.javapro11.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.repository.PostRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
            currentPerson.setTown(profileEditRequest.getTown());
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

	public ResponseEntity<?> getUserWall(long userId, long offset, int itemPerPage) { //TODO Stub
        Person person = personRepository.findById(userId);
        Pageable pageable = getPageable(offset, itemPerPage);

        Page<Post> postPage = postRepository.findAllWherePerson(person, pageable);
        //build response
        return null;
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
            long offset,
            Long itemPerPage
    ) {

        return null;
    }

    public Object blockUser(boolean isBlocked, long userId) {
        Person person = personRepository.findById(userId);
        person.setBlocked(isBlocked);
        personRepository.save(person);
        return null;
    }

    //TODO TimeZone is default but it must be not
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
