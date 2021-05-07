package skillbox.javapro11.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.DialogRequest;
import skillbox.javapro11.api.response.*;
import skillbox.javapro11.enums.ReadStatus;
import skillbox.javapro11.model.entity.Dialog;
import skillbox.javapro11.model.entity.Message;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Person2Dialog;
import skillbox.javapro11.repository.DialogRepository;
import skillbox.javapro11.repository.MessageRepository;
import skillbox.javapro11.repository.Person2DialogRepository;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.service.DialogsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timur_guliev on 27.04.2021.
 */
@Service
public class DialogsServiceImpl implements DialogsService {

    private final AccountServiceImpl accountServiceImpl;
    private final DialogRepository dialogRepository;
    private final Person2DialogRepository person2DialogRepository;
    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public DialogsServiceImpl(AccountServiceImpl accountServiceImpl, DialogRepository dialogRepository, Person2DialogRepository person2DialogRepository, PersonRepository personRepository, MessageRepository messageRepository) {
        this.accountServiceImpl = accountServiceImpl;
        this.dialogRepository = dialogRepository;
        this.person2DialogRepository = person2DialogRepository;
        this.personRepository = personRepository;
        this.messageRepository = messageRepository;
    }


    @Override
    public CommonResponseData createDialog(DialogRequest dialogRequest) {
        Person ownerDialog = accountServiceImpl.getCurrentPerson();
        DialogResponse dialogData = new DialogResponse();
        Dialog newDialog = createNewDialog(ownerDialog);
        createPerson2Dialog(ownerDialog, newDialog);
        createPerson2Dialog(personRepository.findById(dialogRequest.getUsersIds()[0]), newDialog);
        dialogData.setId(newDialog.getId());
        return new CommonResponseData(dialogData, "string");
    }

    @Override
    public CommonResponseData deleteDialog(long id) {
        Dialog dialog = dialogRepository.findById(id);
        dialog.setDeleted(false);
        dialogRepository.save(dialog);
        DialogResponse dialogData = new DialogResponse();
        dialogData.setId(id);
        return new CommonResponseData(dialogData, "string");
    }

    @Override
    public Dialog createNewDialog(Person ownerDialog){
        Dialog dialog = new Dialog();
        dialog.setOwner(ownerDialog);
        return dialogRepository.save(dialog);
    }

    @Override
    public CommonResponseData deleteUsersInDialog(long idDialog, List<String> usersIds) {
        for (int i = 0; i < usersIds.size(); i++){
            person2DialogRepository.deletePersInDialog(idDialog, Long.parseLong(usersIds.get(i)));
        }
        DialogUserShortListResponse dialogData = new DialogUserShortListResponse();
        dialogData.setUserIds(usersIds);
        return new CommonResponseData(dialogData, "string");
    }

    @Override
    public CommonResponseData joinToDialog(long idDialog, String link) {
        Person currentPerson = accountServiceImpl.getCurrentPerson();
        List<String> usersIds = new ArrayList<>();
        usersIds.add(String.valueOf(currentPerson.getId()));
        createPerson2Dialog(currentPerson, dialogRepository.findById(idDialog));
        DialogUserShortListResponse dialogData = new DialogUserShortListResponse();
        dialogData.setUserIds(usersIds);
        return new CommonResponseData(dialogData, "string");
    }

    @Override
    public CommonResponseData sendMessage(long idDialog, String messageText) {
        Person currentPerson = accountServiceImpl.getCurrentPerson();
        createMessage(messageText, currentPerson);

        MessageResponse dialogData = getMessageResponse(messageText, currentPerson);
        return new CommonResponseData(dialogData, "string");
    }



    @Override
    public CommonResponseData editMessage(long idDialog, long idMessage, String messageText) {
        Message message = messageRepository.findById(idMessage);
        message.setText(messageText);
        messageRepository.save(message);
        Person currentPerson = accountServiceImpl.getCurrentPerson();
        MessageResponse dialogData = getMessageResponse(messageText, currentPerson);
        return new CommonResponseData(dialogData, "string");
    }

    @Override
    public CommonResponseData readMessage(long idDialog, long idMessage) {
        Message message = messageRepository.findById(idMessage);
        message.setReadStatus(ReadStatus.READ);
        messageRepository.save(message);
        MessageResponse dialogData = new MessageResponse();
        dialogData.setMessage("ok");
        return new CommonResponseData(dialogData, "string");
    }

    @Override
    public CommonResponseData changeStatusActivity(long idDialog, long idUser) {
        StatusMessageResponse statusMessageResponse = new StatusMessageResponse();
        statusMessageResponse.setMessage("ok");
        return new CommonResponseData(statusMessageResponse, "string");
    }

    private MessageResponse getMessageResponse(String messageText, Person currentPerson) {
        MessageResponse dialogData = new MessageResponse();
        dialogData.setId(currentPerson.getId());
        dialogData.setTime(LocalDateTime.now());
        dialogData.setAuthorId(currentPerson.getId());
        dialogData.setMessageText(messageText);
        dialogData.setReadStatus("SENT");
        return dialogData;
    }

    private void createMessage(String messageText, Person currentPerson) {
        Message newMessage = new Message();
        newMessage.setAuthor(currentPerson);
        newMessage.setText(messageText);
        newMessage.setTime(LocalDateTime.now());
        newMessage.setReadStatus(ReadStatus.SENT);
        messageRepository.save(newMessage);
    }

    private void createPerson2Dialog(Person ownerDialog, Dialog dialog) {
        Person2Dialog person2Dialog = new Person2Dialog();
        person2Dialog.setDialog(dialog);
        person2Dialog.setPerson(ownerDialog);
        person2DialogRepository.save(person2Dialog);
    }
}
