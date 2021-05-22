package skillbox.javapro11.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.javapro11.api.request.LikeRequest;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.LikeResponse;
import skillbox.javapro11.api.response.ListLikeResponse;
import skillbox.javapro11.enums.LikeType;
import skillbox.javapro11.model.entity.*;
import skillbox.javapro11.repository.*;
import skillbox.javapro11.service.AccountService;
import skillbox.javapro11.service.LikeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
	private final AccountService accountService;
	private final PostLikeRepository postLikeRepository;
	private final CommentRepository commentRepository;
	private final PersonRepository personRepository;
	private final PostRepository postRepository;
	private final CommentLikeRepository commentLikeRepository;

	@Autowired
	public LikeServiceImpl(
			PostLikeRepository postLikeRepository,
			AccountService accountService,
			PersonRepository personRepository,
			CommentRepository commentRepository,
			PostRepository postRepository,
			CommentLikeRepository commentLikeRepository
	) {
		this.postLikeRepository = postLikeRepository;
		this.accountService = accountService;
		this.personRepository = personRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.commentLikeRepository = commentLikeRepository;
	}

	@Override
//	@Transactional
	public CommonResponseData isLiked(Long userId, long itemId, String type) {
		boolean isLiked = false;
		Person currentPerson;
		if (userId == null) {
			currentPerson = accountService.getCurrentPerson();
		} else {
			currentPerson = personRepository.getOne(userId);
		}

		if (type.equals(LikeType.POST.getType())) {
			isLiked = postLikeRepository.findByPersonAndPost(currentPerson, itemId) != null;
		}
		if (type.equals(LikeType.COMMENT.getType())) {
			isLiked = commentLikeRepository.findByPersonAndComment(currentPerson, itemId) != null;
		}

		return new CommonResponseData(new LikeResponse(isLiked), "string");
	}

	@Override
	public CommonResponseData getUsersWhoLiked(long itemId, String type) {
		List<Long> usersId = new ArrayList<>();
		if (type.equals(LikeType.POST.getType())) {
			usersId.addAll(postLikeRepository.getAllUsersIdWhiLikePost(itemId));
		}
		if (type.equals(LikeType.COMMENT.getType())) {
			usersId.addAll(commentLikeRepository.getAllUsersIdWhiLikePost(itemId));
		}

		return new CommonResponseData(new ListLikeResponse(usersId.size(), usersId), "string");
	}

	@Override
	@Transactional
	public CommonResponseData putLike(LikeRequest likeRequest) {
		Person currentPerson = accountService.getCurrentPerson();
		if (likeRequest.getType().equals(LikeType.COMMENT.getType())) {
			CommentLike like = new CommentLike();
			like.setComment(commentRepository.getOne(likeRequest.getItemId()));
			like.setPerson(personRepository.getOne(currentPerson.getId()));
			like.setTime(LocalDateTime.now());
			commentLikeRepository.save(like);
		}
		if (likeRequest.getType().equals(LikeType.POST.getType())) {
			PostLike like = new PostLike();
			like.setPerson(currentPerson);
			like.setPost(postRepository.getOne(likeRequest.getItemId()));
			like.setTime(LocalDateTime.now());
			postLikeRepository.save(like);
		}
		return getUsersWhoLiked(likeRequest.getItemId(), likeRequest.getType());
	}

	@Override
	@Transactional
	public CommonResponseData deleteLike(long itemId, String type) {
		if (type.equals(LikeType.POST.getType())) {
			postLikeRepository.deleteByPostId(itemId);
		}
		if (type.equals(LikeType.COMMENT.getType())) {
			commentLikeRepository.deleteByCommentId(itemId);
		}
		CommonResponseData response = getUsersWhoLiked(itemId, type);
		ListLikeResponse likeList = (ListLikeResponse) response.getData();
		likeList.setUsersId(null);
		return response;
	}
}
