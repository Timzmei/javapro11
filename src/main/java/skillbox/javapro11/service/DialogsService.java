package skillbox.javapro11.service;

import skillbox.javapro11.api.response.DialogResponse;

/**
 * Created by timur_guliev on 27.04.2021.
 */
public interface DialogsService {
    DialogResponse getDialogs(Integer offset, Integer itemPerPage, String query);
}
