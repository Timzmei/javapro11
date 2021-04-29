package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.api.request.LikeRequest;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.service.LikeService;

@Controller
public class LikeController {

	private final LikeService likeService;

	@Autowired
	public LikeController(LikeService likeService) {
		this.likeService = likeService;
	}

	@GetMapping("/liked")
	public CommonResponseData isLiked(
			@RequestParam(name = "user_id", required = false) Long userId,
			@RequestParam("item_id") long itemId,
			@RequestParam("type") String type
	) {
		return likeService.isLiked(userId, itemId, type);
	}

	@GetMapping("/likes")
	public CommonResponseData getUsersWhoLiked(
			@RequestParam("item_id") long itemId,
			@RequestParam("type") String type
	) {
		return likeService.getUsersWhoLiked(itemId, type);
	}

	@PutMapping("/likes")
	public CommonResponseData putLike(@RequestBody LikeRequest likeRequest) {
		return likeService.putLike(likeRequest);
	}

	@DeleteMapping("/likes")
	public CommonResponseData deleteLike(
			@RequestParam("item_id") long itemId,
			@RequestParam("type") String type
	) {
		return likeService.deleteLike(itemId, type);
	}

}
