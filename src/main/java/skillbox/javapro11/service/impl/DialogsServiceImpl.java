package skillbox.javapro11.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.DialogRequest;
import skillbox.javapro11.api.response.*;
import skillbox.javapro11.model.entity.Dialog;
import skillbox.javapro11.model.entity.Message;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Person2Dialog;
import skillbox.javapro11.repository.DialogRepository;
import skillbox.javapro11.repository.MessageRepository;
import skillbox.javapro11.repository.Person2DialogRepository;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.repository.util.Utils;
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
    public Dialog createNewDialog(Person ownerDialog) {
        Dialog dialog = new Dialog();
        dialog.setOwner(ownerDialog);
        return dialogRepository.save(dialog);
    }

    @Override
    public CommonResponseData deleteUsersInDialog(long idDialog, String[] usersIds) {
        for (int i = 0; i < usersIds.length; i++) {
//            person2DialogRepository.deletePrsonInDialog(idDialog, usersIds[i]);
        }

//        Dialog dialog = dialogRepository.findById(id);
//        dialog.setDeleted(false);
//        dialogRepository.save(dialog);
//        DialogResponse dialogData = new DialogResponse();
//        dialogData.setId(id);
//        return new CommonResponseData(dialogData, "string");
        return null;
    }

    private void createPerson2Dialog(Person ownerDialog, Dialog dialog) {
        Person2Dialog person2Dialog = new Person2Dialog();
        person2Dialog.setDialog(dialog);
        person2Dialog.setPerson(ownerDialog);
        person2DialogRepository.save(person2Dialog);
    }

    @Override
    public CommonListResponse getDialogs(Integer offset, Integer itemPerPage, String query) {
        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage);
        Person currentPerson = accountServiceImpl.getCurrentPerson();
        Page<Dialog> dialogPage;
        if (query.equals(""))
        {
            dialogPage = dialogRepository.getDialogsOfPerson(pageable, currentPerson);
        }else {
            dialogPage = dialogRepository.getDialogsOfPersonWithQuery(pageable, query, currentPerson);
        }

        CommonListResponse cListResponse = new CommonListResponse();
        cListResponse.setError("string");
        cListResponse.setTimestamp(LocalDateTime.now());
        //All items with query filter
        cListResponse.setTotal(dialogPage.getTotalElements());
        cListResponse.setOffset(offset);
        cListResponse.setPerPage(itemPerPage);

        List<ResponseData> dialogResponses = new ArrayList<>();
        for (Dialog dialog : dialogPage) {
            DialogResponse dialogResponse = new DialogResponse();
            dialogResponse.setId(dialog.getId());
            //All unread messages in the dialog
            dialogResponse.setUnreadCount(messageRepository.getUnreadCountOfDialog(dialog));
            //Last message {
            Message lastMessage = messageRepository.getLastMessageOfDialog(dialog);
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setId(lastMessage.getId());
            messageResponse.setTime(Utils.getLongFromLocalDateTime(lastMessage.getTime()));
            messageResponse.setAuthorId(lastMessage.getAuthor().getId());
            messageResponse.setRecipientId(lastMessage.getRecipient().getId());
            messageResponse.setMessageText(lastMessage.getText());
            messageResponse.setReadStatus(lastMessage.getReadStatus().toString());
            //}

            dialogResponse.setLastMessage(messageResponse);
            dialogResponses.add(dialogResponse);
        }
        cListResponse.setData(dialogResponses);

        return cListResponse;
    }

    @Override
    public CommonResponseData getQuantityUnreadMessageOfPerson() {
        Person currentPerson = accountServiceImpl.getCurrentPerson();
        int quantityUnreadMessage = messageRepository.getUnreadCountOfPerson(currentPerson);

        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setError("string");
        commonResponseData.setTimestamp(LocalDateTime.now());

        CountMessageResponse countMessageResponse = new CountMessageResponse(quantityUnreadMessage);
        commonResponseData.setData(countMessageResponse);
        return commonResponseData;
    }

    @Override
    public ResponseArrayUserIds addUserIntoDialog(long id, DialogRequest dialogRequest) {

        Dialog dialog = dialogRepository.findById(id);
        if(dialog == null && dialogRequest.getUsersIds().length > 0)
        {
            //TODO что вернуть при ошибки пока не понятно
            return null;
        }
        List<Person2Dialog> listP2D = new ArrayList<>();
        List<Long> idForResponse = new ArrayList<>();
        for(long personId : dialogRequest.getUsersIds())
        {
            Person personIntoDialog = personRepository.findById(personId);
            idForResponse.add(personId);
            if (personIntoDialog != null)
            {
                Person2Dialog person2Dialog = new Person2Dialog();
                person2Dialog.setPerson(personIntoDialog);
                person2Dialog.setDialog(dialog);
                listP2D.add(person2Dialog);
            }
            person2DialogRepository.saveAll(listP2D);

        }
        ResponseArrayUserIds responseArrayUserIds = new ResponseArrayUserIds();
        responseArrayUserIds.setError("string");
        responseArrayUserIds.setTimestamp(LocalDateTime.now());
        responseArrayUserIds.setUsersIds(idForResponse);

        return responseArrayUserIds;
    }

    @Override
    public CommonResponseData getInviteDialog(long idDialog) {
        String invite = dialogRepository.getInviteByDialog(idDialog);

        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setError("string");
        commonResponseData.setTimestamp(LocalDateTime.now());
        LinkResponse linkResponse = new LinkResponse(invite);
        commonResponseData.setData(linkResponse);

        return commonResponseData;
    }

    @Override
    public CommonListResponse getMessageOfDialog(long idDialog, Integer offset, Integer itemPerPage, String query) {
        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage);
        Page<Message> listMessage = messageRepository.getMessageOfDialog(pageable, query, idDialog);

        CommonListResponse cListResponse = new CommonListResponse();
        cListResponse.setError("string");
        cListResponse.setTimestamp(LocalDateTime.now());

        cListResponse.setTotal(listMessage.getTotalElements());
        cListResponse.setOffset(offset);
        cListResponse.setPerPage(itemPerPage);

        List<ResponseData> messageResponses = new ArrayList<>();
        for (Message message : listMessage) {
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setId(message.getId());
            messageResponse.setTime(Utils.getLongFromLocalDateTime(message.getTime()));
            messageResponse.setAuthorId(message.getAuthor().getId());
            messageResponse.setRecipientId(message.getRecipient().getId());
            messageResponse.setMessageText(message.getText());
            messageResponse.setReadStatus(message.getReadStatus().toString());

            messageResponses.add(messageResponse);
        }
        cListResponse.setData(messageResponses);

        return cListResponse;
    }

    @Override
    public CommonResponseData deleteMessage(long idMessage, long idDialog) {
        Message message = messageRepository.findByIdAndDialog(idMessage, idDialog);
        if(message != null)
        {
            message.setDeleted(true);
            messageRepository.save(message);
        }else
        {
            idMessage = 0;
        }

        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setError("string");
        commonResponseData.setTimestamp(LocalDateTime.now());
        IdMessageResponse idMessageResponse = new IdMessageResponse(idMessage);
        commonResponseData.setData(idMessageResponse);

        return commonResponseData;
    }

    @Override
    public CommonResponseData recoverMessage(long idMessage, long idDialog) {
        Message message = messageRepository.findByIdAndDialog(idMessage, idDialog);
        if(message != null)
        {
            message.setDeleted(false);
            messageRepository.save(message);
        }

        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setError("string");
        commonResponseData.setTimestamp(LocalDateTime.now());
        //Message{
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setId(message.getId());
        messageResponse.setTime(Utils.getLongFromLocalDateTime(message.getTime()));
        messageResponse.setAuthorId(message.getAuthor().getId());
        messageResponse.setRecipientId(message.getRecipient().getId());
        messageResponse.setMessageText(message.getText());
        messageResponse.setReadStatus(message.getReadStatus().toString());
        //}
        commonResponseData.setData(messageResponse);

        return commonResponseData;
    }

    @Override
    public CommonResponseData getStatusAndLastActivity(long idPerson, long idDialog) {

        CommonResponseData commonResponseData = new CommonResponseData();
        commonResponseData.setError("string");
        commonResponseData.setTimestamp(LocalDateTime.now());
        UserStatusResponse userStatusResponse = new UserStatusResponse();
        /**Сессия не храниться, статус неизвестен*/
        boolean status = false;
        Dialog dialog = dialogRepository.findById(idDialog);
        Person person = personRepository.findById(idPerson);

        if(dialog != null && person != null) {
            LocalDateTime lastActivityInTheDialogFromPerson = messageRepository.getTheTimeOfTheLastMessageOfTheDialogFromThePerson(dialog, person);
            userStatusResponse.setOnline(status);
            userStatusResponse.setLastActivity(Utils.getLongFromLocalDateTime(lastActivityInTheDialogFromPerson));
        }

        commonResponseData.setData(userStatusResponse);

        return commonResponseData;
    }

}





















