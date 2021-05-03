package skillbox.javapro11.service;

import skillbox.javapro11.api.request.DialogRequest;
import skillbox.javapro11.api.response.CommonResponseData;
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

    CommonResponseData deleteUsersInDialog(long idDialog, String[] usersIds);

    DialogResponse getDialogs(Integer offset, Integer itemPerPage, String query);
}
