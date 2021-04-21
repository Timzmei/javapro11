package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.response.CommonResponseData;
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
    public ResponseEntity<?>  getPostSearch (){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") long postId){
        return new ResponseEntity<>(postService.getPostByID(postId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editPostById(@PathVariable("id") long postId,
                             @PathVariable("publish_data") long publishData,
                             @RequestBody PostRequest post){
        CommonResponseData commonResponseData = postService.editPostById(postId, publishData, post);
        return new ResponseEntity<>(commonResponseData, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable("id") long postId){
        return new ResponseEntity<>(postService.deletePostById(postId), HttpStatus.OK);
    }

    @PutMapping("/{id}/recover")
    public ResponseEntity<?> recoverPostById(@PathVariable("id") long postId){
        return new ResponseEntity<>(postService.recoverPostById(postId), HttpStatus.OK);
    }

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
    public ResponseEntity<?> reportPost(@PathVariable("id") long postId){
        return new ResponseEntity<>(postService.reportPost(postId), HttpStatus.OK);
    }

    @PostMapping("/{id}/comments/{comment_id}/report")
    public void reportCommentOnPost(){}
}
