package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.api.response.CommonListResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.PersonResponse;
import skillbox.javapro11.service.impl.ProfileServiceImpl;

@RestController
@RequestMapping("/api/v1/users")
public class ProfileController {

    private final ProfileServiceImpl profileServiceImpl;

    @Autowired
    public ProfileController(
            ProfileServiceImpl profileServiceImpl
    ) {
        this.profileServiceImpl = profileServiceImpl;
    }

    @GetMapping("/me")
    public PersonResponse getCurrentUser() {
        return profileServiceImpl.getCurrentUser();
    }

    @PutMapping("/me")
    public PersonResponse editCurrentUser(@RequestBody ProfileEditRequest profileEditRequest) {
        return profileServiceImpl.editCurrentUser(profileEditRequest);
    }

    @DeleteMapping("/me")
    public CommonResponseData deleteCurrentUser() {
        return profileServiceImpl.deleteCurrentUser();
    }

    @GetMapping("/{id}")
    public PersonResponse getUserById(@PathVariable("id") long id) {
        return profileServiceImpl.findUserById(id);
    }

    @GetMapping("/{id}/wall")
    public CommonListResponse getPostsUserWall(
            @PathVariable("id") long id,
            @RequestParam("offset") long offset,
            @RequestParam("itemPerPage") int itemPerPage) {
        return profileServiceImpl.getUserWall(id, offset, itemPerPage);
    }

    @PostMapping("/{id}/wall")
    public CommonResponseData addPostUserWall(
            @PathVariable("id") long id,
            @RequestParam("publish_date") long publishDate,
            @RequestBody PostRequest postRequestBody) {
        return profileServiceImpl.postOnUserWall(id, publishDate, postRequestBody);
    }

    @GetMapping("/search")
    public CommonListResponse searchUser(
            @RequestParam(name = "first_name", required = false) String firstName,
            @RequestParam(name = "last_name", required = false) String lastName,
            @RequestParam(name = "age_from", required = false) Integer ageFrom,
            @RequestParam(name = "age_to", required = false) Integer ageTo,
            @RequestParam(name = "country_id", required = false) String country,
            @RequestParam(name = "city_id", required = false) String city,
            @RequestParam("offset") long offset,
            @RequestParam("itemPerPage") int itemPerPage
    ) {
        return profileServiceImpl
                .searchUser(firstName, lastName, ageFrom, ageTo, country, city, offset, itemPerPage);
    }

    @PutMapping("/block/{id}")
    public CommonResponseData blockUserById(@PathVariable("id") long id) {
        return profileServiceImpl.blockUser(true, id);
    }

    @DeleteMapping("/block/{id}")
    public CommonResponseData unblockUserById(@PathVariable("id") long id) {
        return profileServiceImpl.blockUser(false, id);
    }
}
