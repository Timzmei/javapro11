package skillbox.javapro11.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.request.DialogRequest;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.DialogResponse;
import skillbox.javapro11.model.entity.Dialog;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Person2Dialog;
import skillbox.javapro11.repository.DialogRepository;
import skillbox.javapro11.repository.Person2DialogRepository;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.service.DialogsService;

/**
 * Created by timur_guliev on 27.04.2021.
 */
@Service
public class DialogsServiceImpl implements DialogsService {

    private final AccountServiceImpl accountServiceImpl;
    private final DialogRepository dialogRepository;
    private final Person2DialogRepository person2DialogRepository;
    private final PersonRepository personRepository;

    @Autowired
    public DialogsServiceImpl(AccountServiceImpl accountServiceImpl, DialogRepository dialogRepository, Person2DialogRepository person2DialogRepository, PersonRepository personRepository) {
        this.accountServiceImpl = accountServiceImpl;
        this.dialogRepository = dialogRepository;
        this.person2DialogRepository = person2DialogRepository;
        this.personRepository = personRepository;
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
    public CommonResponseData deleteUsersInDialog(long idDialog, String[] usersIds) {
        for (int i = 0; i < usersIds.length; i++){
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
    public DialogResponse getDialogs(Integer offset, Integer itemPerPage, String query) {
        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage);

        Page<Dialog> dialogPage;

        //dialogPage = dialogRepository.getDialogsByQuery(pageable, query);


        return null;
    }
}
