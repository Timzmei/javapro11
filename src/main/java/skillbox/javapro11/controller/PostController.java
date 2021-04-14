package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.response.PostResponse;
import skillbox.javapro11.service.post.PostService;

/**
 * Created by Artem on 03.04.2021.
 */

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("")
    public PostResponse getPostSearch (){
        return null;
    }

    @GetMapping("/{id}")
    public PostResponse getPostById(){
        return null;
    }

    @PutMapping("/{id}")
    public void editPostById(){}

    @DeleteMapping("/{id}")
    public void deletePostById(){}

    @PutMapping("/{id}/recover")
    public void recoverPostById(){}

    @GetMapping("/{id}/comments")
    public void getCommentsOnPost(){}

    @PostMapping("/{id}/comments")
    public void creationCommentForPost(){}

    @PutMapping("/{id}/comments/{comment_id}")
    public void editCommentForPost(){}

    @DeleteMapping("/{id}/comments/{comment_id}")
    public void deleteCommentForPost(){}

    @PutMapping("/{id}/comments/{comment_id}/recover")
    public void recoverComment(){}

    @PostMapping("/{id}/report")
    public void reportPost(){}

    @PostMapping("/{id}/comments/{comment_id}/report")
    public void reportCommentOnPost(){}
}
