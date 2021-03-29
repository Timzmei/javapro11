package skillbox.javapro11.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.service.ProfileService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<Person> getCurrentUser() {
        Person person = this.profileService.getCurrentUser();
        return ResponseEntity.ok(person);
    }

    @PutMapping("/me")
    public ResponseEntity<Person> editCurrentUser(@RequestBody ProfileEditRequest profileEditRequest) {
        return ResponseEntity.ok(profileService.editCurrentUser(profileEditRequest));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteCurrentUser() {
        profileService.deleteCurrentUser();
        return ResponseEntity.ok(new Person()); // Just to avoid compile error, fix it later
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getUserById(@PathVariable("id") long id) {
        Person person = profileService.findUserById(id);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/{id}/wall")
    @PreAuthorize("")
    public void getPostsUserWall() {
    }

    @PostMapping("/{id}/wall")
    public void addPostUserWall() {
    }

    @GetMapping("/search")
    public void searchUser() {
    }

    @PutMapping("/block/{id}")
    public void blockUserById() {
    }

    @DeleteMapping("/block/{id}")
    public void unblockUserById() {
    }
}
