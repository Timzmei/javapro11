package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.service.ProfileService;

@RestController
@RequestMapping("/api/v1/users")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(
            ProfileService profileService
    ) {
        this.profileService = profileService;
    }

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
    public ResponseEntity<?> getPostsUserWall(
            @PathVariable("id") long id,
            @RequestParam("offset") long offset,
            @RequestParam("itemPerPage") int itemPerPage) { //TODO change generic to real class
        return ResponseEntity.ok(profileService.getUserWall(id, offset, itemPerPage));
    }

    @PostMapping("/{id}/wall")
    public ResponseEntity<?> addPostUserWall(
            @PathVariable("id") long id,
            @RequestParam("publish_date") long publishDate,
            @RequestBody PostRequestBody postRequestBody) {
        return ResponseEntity.ok(profileService.postOnUserWall(id, publishDate, postRequestBody));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(
            @RequestParam(name = "first_name", required = false) String firstName,
            @RequestParam(name = "last_name", required = false) String lastName,
            @RequestParam(name = "age_from", required = false) Integer ageFrom,
            @RequestParam(name = "age_to", required = false) Integer ageTo,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam("offset") long offset,
            @RequestParam("itemPerPage") Long itemPerPage
    ) {
        return ResponseEntity.ok(profileService
                .searchUser(firstName, lastName, ageFrom, ageTo, country, offset, itemPerPage));
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<?> blockUserById(@PathVariable("id") long id) {
        return ResponseEntity.ok(profileService.blockUser(true, id));
    }

    @DeleteMapping("/block/{id}")
    public ResponseEntity<?> unblockUserById(@PathVariable("id") long id) {
        return ResponseEntity.ok(profileService.blockUser(false, id));
    }
}
