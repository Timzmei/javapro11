package skillbox.javapro11.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by Artem on 03.04.2021.
 */

@RestController
public class PostsController {

    @GetMapping("/api/v1/post/")
    public void searchPublication (){}

    @GetMapping("/api/v1/post/{id}")
    public void getPublicationById() {}

    @PutMapping("/api/v1/post/{id}")
    public void editPublicationById(){}

    @DeleteMapping("/api/v1/post/{id}")
    public void deletePublicationById(){}

    @PutMapping("/api/v1/post/{id}/recover")
    public void recoverPublicationById(){}

    @GetMapping("/api/v1/post/{id}/comments")
    public void getCommentsOnPublication(){}

    @PostMapping("/api/v1/post/{id}/comments")
    public void creationCommentForPublication(){}

    @PutMapping("/api/v1/post/{id}/comments/{comment_id}")
    public void editCommentForPublication(){}

    @DeleteMapping("/api/v1/post/{id}/comments/{comment_id}")
    public void deleteCommentForPublication(){}

    @PutMapping("/api/v1/post/{id}/comments/{comment_id}/recover")
    public void recoverComment(){}

    @PostMapping("/api/v1/post/{id}/report")
    public void reportPublication(){}

    @PostMapping("/api/v1/post/{id}/comments/{comment_id}/report")
    public void reportCommentOnPublication(){}
}
