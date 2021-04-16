package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.CommentRequest;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.service.PostService;

/**
 * Created by Artem on 03.04.2021.
 */

@RestController
@RequestMapping("/post")
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
    public ResponseEntity<?> createComment(@PathVariable(name = "id") long idPost, @RequestBody CommentRequest comment){
        CommonResponseData commonResponseData = postService.editedComment(idPost, 0, comment);
        return new ResponseEntity<>(commonResponseData, HttpStatus.OK);
    }

    @PutMapping("/{id}/comments/{comment_id}")
    public ResponseEntity<?> editComment(@PathVariable(name = "id") long idPost,
                                         @PathVariable(name = "comment_id") long idComment, @RequestBody CommentRequest comment){
        CommonResponseData commonResponseData = postService.editedComment(idPost, idComment, comment);
        return new ResponseEntity<>(commonResponseData, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") long idPost,@PathVariable(name = "comment_id") long idComment){
        CommonResponseData commonResponseData = postService.deleteComment(idPost, idComment);
        return new ResponseEntity<>(commonResponseData, HttpStatus.OK);
    }

    @PutMapping("/{id}/comments/{comment_id}/recover")
    public ResponseEntity<?> recoverComment(@PathVariable(name = "id") long idPost,@PathVariable(name = "comment_id") long idComment){
        return new ResponseEntity<>(postService.recoverComment(idPost, idComment), HttpStatus.OK);
    }

    @PostMapping("/{id}/report")
    public void reportPublication(){}

    @PostMapping("/{id}/comments/{comment_id}/report")
    public ResponseEntity<?> reportComment(@PathVariable(name = "id") long idPost,@PathVariable(name = "comment_id") long idComment){
        return new ResponseEntity<>(postService.reportComment(idPost, idComment) ,HttpStatus.OK);
    }
}
