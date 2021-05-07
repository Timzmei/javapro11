package skillbox.javapro11.service;

import skillbox.javapro11.api.request.DialogRequest;
import skillbox.javapro11.api.response.CommonListResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.ResponseArrayUserIds;
import skillbox.javapro11.model.entity.Dialog;
import skillbox.javapro11.model.entity.Person;

import skillbox.javapro11.api.response.DialogResponse;

/**
 * Created by timur_guliev on 27.04.2021.
 */
public interface DialogsService {
    CommonResponseData createDialog(DialogRequest dialogRequest);

    CommonResponseData deleteDialog(long id);

    Dialog createNewDialog(Person ownerDialog);

    CommonResponseData deleteUsersInDialog(long idDialog, List<String> usersIds);

    CommonResponseData joinToDialog(long idDialog, String link);

    CommonResponseData sendMessage(long idDialog, String messageText);

    CommonResponseData editMessage(long idDialog, long idMessage, String messageText);

    CommonResponseData readMessage(long idDialog, long idMessage);

    CommonResponseData changeStatusActivity(long idDialog, long idUser);

    CommonResponseData deleteUsersInDialog(long idDialog, String[] usersIds);

    CommonListResponse getDialogs(Integer offset, Integer itemPerPage, String query);

    CommonResponseData getQuantityUnreadMessageOfPerson();

    ResponseArrayUserIds addUserIntoDialog(long id, DialogRequest dialogRequest);

    CommonResponseData getInviteDialog(long idDialog);

    CommonListResponse getMessageOfDialog(long idDialog, Integer offset, Integer itemPerPage, String query);

    CommonResponseData deleteMessage( long idMessage, long idDialog);

    CommonResponseData recoverMessage(long idMessage, long idDialog);

    CommonResponseData getStatusAndLastActivity(long idPerson, long idDialog);
}
