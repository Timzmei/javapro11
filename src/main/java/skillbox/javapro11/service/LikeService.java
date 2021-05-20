package skillbox.javapro11.service;

import skillbox.javapro11.api.request.LikeRequest;
import skillbox.javapro11.api.response.CommonResponseData;

public interface LikeService {
	CommonResponseData isLiked(Long userId, long itemId, String type);

	CommonResponseData getUsersWhoLiked(long itemId, String type);

	CommonResponseData putLike(LikeRequest likeRequest);

	CommonResponseData deleteLike(long itemId, String type);
}
