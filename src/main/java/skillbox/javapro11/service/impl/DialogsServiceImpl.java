package skillbox.javapro11.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.response.DialogResponse;
import skillbox.javapro11.model.entity.Dialog;
import skillbox.javapro11.repository.DialogRepository;
import skillbox.javapro11.service.DialogsService;

/**
 * Created by timur_guliev on 27.04.2021.
 */
@Service
public class DialogsServiceImpl implements DialogsService {

    private final DialogRepository dialogRepository;

    public DialogsServiceImpl(DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public DialogResponse getDialogs(Integer offset, Integer itemPerPage, String query) {
        Pageable pageable = PageRequest.of(offset / itemPerPage, itemPerPage);

        Page<Dialog> dialogPage;

        //dialogPage = dialogRepository.getDialogsByQuery(pageable, query);


        return null;
    }
}
