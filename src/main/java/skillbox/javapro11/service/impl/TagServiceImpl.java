package skillbox.javapro11.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.model.entity.Tag;
import skillbox.javapro11.repository.TagRepository;
import skillbox.javapro11.service.TagService;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Artem on 21.04.2021.
 */

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    TagRepository tagRepository;

    @Override
    public CommonResponseData getTag(String tag, long offset, int limit) {
        return null;
    }

    @Override
    public CommonResponseData createTag() {
        return null;
    }

    @Override
    public CommonResponseData deleteTag(long idTag) {
        CommonResponseData response = new CommonResponseData();
        Optional<Tag> optionalTag = tagRepository.findById(idTag);
        if (optionalTag.isEmpty()) {
            response.setError(idTag + " not found");
            response.setTimestamp(LocalDateTime.now());
            return response;
        }
        Tag tag = optionalTag.get();
        tagRepository.delete(tag);

        return null;
    }
}
