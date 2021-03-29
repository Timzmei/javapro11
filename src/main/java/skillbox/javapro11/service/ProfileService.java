package skillbox.javapro11.service;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;

@Service
@AllArgsConstructor
public class ProfileService {

    private final PersonRepository personRepository;

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
}
