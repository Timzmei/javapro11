package skillbox.javapro11.service;

import skillbox.javapro11.api.response.CommonResponseData;

/**
 * Created by Artem on 21.04.2021.
 */
public interface TagService {
    CommonResponseData getTag (String tag, long offset, int limit);
    CommonResponseData createTag ();
    CommonResponseData deleteTag(long idTag);
}
