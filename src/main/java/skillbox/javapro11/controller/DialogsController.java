package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.DialogRequest;
import skillbox.javapro11.service.DialogsService;

import java.util.List;

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
                                               @PathVariable("users_ids") List<String> usersIds){
        return new ResponseEntity<>(dialogsService.deleteUsersInDialog(idDialog, usersIds), HttpStatus.OK);
    }

    @GetMapping("/{id}/users/invite") //Сергей
    public ResponseEntity getInviteDialog(){
        return null;
    }

    @PutMapping("/{id}/users/join")
    public ResponseEntity joinToDialog(@PathVariable("id") long idDialog,
                                       @RequestBody DialogRequest dialogRequest){
        return new ResponseEntity<>(dialogsService.joinToDialog(idDialog, dialogRequest.getLink()), HttpStatus.OK);
    }

    @GetMapping("/{id}/messages") //Сергей
    public ResponseEntity getHistoryOfMessages(){
        return null;
    }

    @PostMapping("/{id}/messages")
    public ResponseEntity sendMessages(@PathVariable("id") long idDialog,
                                       @RequestBody DialogRequest dialogRequest){
        return new ResponseEntity<>(dialogsService.sendMessage(idDialog, dialogRequest.getMessageText()), HttpStatus.OK);
    }

    @DeleteMapping("/{dialog_id}/messages/{message_id}") //Сергей
    public ResponseEntity deleteMessage(){
        return null;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}")
    public ResponseEntity editMessage(@PathVariable("dialog_id") long idDialog,
                                      @PathVariable("message_id") long idMessage,
                                      @RequestBody DialogRequest dialogRequest){
        return new ResponseEntity<>(dialogsService.editMessage(idDialog, idMessage, dialogRequest.getMessageText()), HttpStatus.OK);
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/recover") //Сергей
    public ResponseEntity recoverMessage(){
        return null;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/read")
    public ResponseEntity readMessage(@PathVariable("dialog_id") long idDialog,
                                      @PathVariable("message_id") long idMessage){
        return new ResponseEntity<>(dialogsService.readMessage(idDialog, idMessage), HttpStatus.OK);
    }

    @GetMapping("/{id}/activity/{user_id}") //Сергей
    public ResponseEntity getStatus(){
        return null;
    }

    @PostMapping("/{id}/activity/{user_id}")
    public ResponseEntity changeStatus(@PathVariable("id") long idDialog,
                                       @PathVariable("user_id") long idUser){
        return new ResponseEntity<>(dialogsService.changeStatusActivity(idDialog, idUser), HttpStatus.OK);
    }

    @GetMapping("/longpoll") // не реализовано во фронте
    public ResponseEntity getLongpoll(){
        return null;
    }

    @PostMapping("/longpoll/history") // не реализовано во фронте
    public ResponseEntity updateUserMessages(){
        return null;
    }
}
