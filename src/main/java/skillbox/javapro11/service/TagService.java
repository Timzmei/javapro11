package skillbox.javapro11.service;

import skillbox.javapro11.api.response.CommonListResponse;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.TagResponse;

/**
 * Created by Artem on 21.04.2021.
 */
public interface TagService {
  CommonListResponse getTags(String tag, long offset, int limit);

  CommonResponseData createTag(TagResponse tag);

  CommonResponseData deleteTag(long idTag);
}
