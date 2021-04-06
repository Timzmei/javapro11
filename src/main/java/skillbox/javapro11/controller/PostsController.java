package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.service.post.PostService;

/**
 * Created by Artem on 03.04.2021.
 */

@RestController
@RequestMapping("/api/v1/post")
public class PostsController {
    @Autowired
    private PostService postService;

    @GetMapping("")
    public void searchPublication (){}

    @GetMapping("/{id}")
    public void getPublicationById() {}

    @PutMapping("/{id}")
    public void editPublicationById(){}

    @DeleteMapping("/{id}")
    public void deletePublicationById(){}

    @PutMapping("/{id}/recover")
    public void recoverPublicationById(){}

    @GetMapping("/{id}/comments")
    public void getCommentsOnPublication(){}

    @PostMapping("/{id}/comments")
    public void creationCommentForPublication(){}

    @PutMapping("/{id}/comments/{comment_id}")
    public void editCommentForPublication(){}

    @DeleteMapping("/{id}/comments/{comment_id}")
    public void deleteCommentForPublication(){}

    @PutMapping("/{id}/comments/{comment_id}/recover")
    public void recoverComment(){}

    @PostMapping("/{id}/report")
    public void reportPublication(){}

    @PostMapping("/{id}/comments/{comment_id}/report")
    public void reportCommentOnPublication(){}
}
