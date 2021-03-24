package skillbox.javapro11.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    @GetMapping("/api/v1/users/me")
    public void getCurrentUser() {}

    @PutMapping("/api/v1/users/me")
    public void editCurrentUser() {}

    @DeleteMapping("/api/v1/users/me")
    public void deleteCurrentUser() {}

    @GetMapping("/api/v1/users/{id}")
    public void getUserById() {}

    @GetMapping("/api/v1/users/{id}/wall")
    public void getPostsUserWall() {}

    @PostMapping("/api/v1/users/{id}/wall")
    public void addPostUserWall() {}

    @GetMapping("/api/v1/users/search")
    public void searchUser() {}

    @PutMapping("/api/v1/users/block/{id}")
    public void blockUserById() {}

    @DeleteMapping("/api/v1/users/block/{id}")
    public void unblockUserById() {}
}
