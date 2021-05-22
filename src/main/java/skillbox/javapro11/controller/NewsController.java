package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skillbox.javapro11.api.response.CommonListResponse;
import skillbox.javapro11.api.response.PostResponse;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.PostRepository;
import skillbox.javapro11.service.AccountService;
import skillbox.javapro11.service.ProfileService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class NewsController {

    private AccountService accountService;

    private PostRepository postRepository; //temporary implementation, in the future must be replaced by PostService

    @Autowired
    public NewsController(AccountService accountService, PostRepository postService, ProfileService profileService) {
        this.accountService = accountService;
        this.postRepository = postService;
    }

    @GetMapping("/feeds")
    public CommonListResponse getFeed(@PageableDefault(sort = "{time}",
            direction = Sort.Direction.DESC) Pageable pageable) {

        Person person = accountService.getCurrentPerson();
        Page<Post> posts = postRepository.findAllByPerson(person, pageable);
        List<PostResponse> postResponseList = PostResponse.fromPostList(posts.toList());
        return new CommonListResponse("", LocalDateTime.now(), new ArrayList<>(postResponseList));
    }
}
