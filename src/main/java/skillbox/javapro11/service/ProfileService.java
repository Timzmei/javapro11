package skillbox.javapro11.service;

import com.sun.istack.NotNull;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.api.response.CommonListResponse;
import skillbox.javapro11.api.response.CommonResponseData;

import java.time.LocalDateTime;

public interface ProfileService {

    CommonResponseData getCurrentUser();

    CommonResponseData editCurrentUser(@NotNull ProfileEditRequest profileEditRequest);

    CommonResponseData deleteCurrentUser();

    CommonResponseData findUserById(long id);

    CommonListResponse getUserWall(long userId, long offset, int itemPerPage);

    CommonResponseData postOnUserWall(long userId, PostRequest postBody);

    CommonListResponse searchUser(
            String firstName,
            String lastName,
            Integer ageFrom,
            Integer ageTo,
            String country,
            String city,
            long offset,
            int itemPerPage
    );

    CommonResponseData blockUser(boolean isBlocked, long userId);

    LocalDateTime getCorrectPublishLocalDateTime(LocalDateTime publishLocalDateTime);

}
