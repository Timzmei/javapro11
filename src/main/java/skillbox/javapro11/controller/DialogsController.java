package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.DialogRequest;
import skillbox.javapro11.service.DialogsService;

/**
 * Created by timur_guliev on 27.04.2021.
 */
@RestController
@RequestMapping("/dialogs")
public class DialogsController {

    private DialogsService dialogsService;

    @Autowired
    public DialogsController(DialogsService dialogsService){
        this.dialogsService = dialogsService;
    }

    @GetMapping("") //Сергей
    public ResponseEntity getDialogsList(){
        return null;
    }

    @PostMapping("")
    public ResponseEntity createDialog(@RequestBody DialogRequest dialogRequest){
        return new ResponseEntity<>(dialogsService.createDialog(dialogRequest), HttpStatus.OK);
    }

    @GetMapping("/unreaded") //Сергей
    public ResponseEntity getCountUnreadedMessages(){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDialog(@PathVariable("id") long id){
        return new ResponseEntity<>(dialogsService.deleteDialog(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/users") //Сергей
    public ResponseEntity addUserIntoDialog(){
        return null;
    }

    @DeleteMapping("/{id}/users")
    public ResponseEntity deleteUserFromDialog(@PathVariable("id") long idDialog,
                                               @PathVariable("users_ids") String[] usersIds){
        return new ResponseEntity<>(dialogsService.deleteUsersInDialog(idDialog, usersIds), HttpStatus.OK);
    }

    @GetMapping("/{id}/users/invite") //Сергей
    public ResponseEntity getInviteDialog(){
        return null;
    }

    @PutMapping("/{id}/users/join")
    public ResponseEntity joinToDialog(){
        return null;
    }

    @GetMapping("/{id}/messages") //Сергей
    public ResponseEntity getHistoryOfMessages(){
        return null;
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity sendMessages(){
        return null;
    }

    @DeleteMapping("/{dialog_id}/messages/{message_id}") //Сергей
    public ResponseEntity deleteMessage(){
        return null;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}")
    public ResponseEntity editMessage(){
        return null;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/recover") //Сергей
    public ResponseEntity recoverMessage(){
        return null;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/read")
    public ResponseEntity readMessage(){
        return null;
    }

    @GetMapping("/{id}/activity/{user_id}") //Сергей
    public ResponseEntity getStatus(){
        return null;
    }

    @PostMapping("/{id}/activity/{user_id}")
    public ResponseEntity changeStatus(){
        return null;
    }

    @GetMapping("/longpoll") //Сергей
    public ResponseEntity getLongpoll(){
        return null;
    }

    @PostMapping("/longpoll/history")
    public ResponseEntity updateUserMessages(){
        return null;
    }
}
