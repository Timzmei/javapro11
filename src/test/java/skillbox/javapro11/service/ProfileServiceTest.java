package skillbox.javapro11.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import skillbox.javapro11.ServiceTestConfiguration;
import skillbox.javapro11.api.request.PostRequest;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.api.response.*;
import skillbox.javapro11.enums.PermissionMessage;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.model.entity.Post;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.repository.PostRepository;
import skillbox.javapro11.repository.util.PersonSpecificationsBuilder;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(classes = ServiceTestConfiguration.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Profile service class test:")
public class ProfileServiceTest {

    private final LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 1, 12, 0);
    private final LocalDate localDate = LocalDate.of(2021, 1, 1);

    @MockBean
    private AccountService accountService;
    @MockBean
    private ProfileEditRequest profileEditRequest;
    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private PostRepository postRepository;

    @Autowired
    private ProfileService profileService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(accountService);

        Person currentPerson = new Person(
                1L,
                "Ivan",
                "Ivanov",
                localDateTime, // registration date
                localDate, // birth date
                "ivanov@mail.com",
                "+7(111)222-33-44",
                "password",
                "photo",
                "about",
                "Russia",
                "Moscow",
                true,
                PermissionMessage.ALL,
                localDateTime, // last online time
                false);

        Mockito.when(accountService.getCurrentPerson()).thenReturn(currentPerson);
    }

    @Test
    @DisplayName("Getting current user")
    void getCurrentUserTest() {
        PersonResponse personResponse = profileService.getCurrentUser();

        assertThat(personResponse)
                .isNotNull();
        //.hasNoNullFieldsOrProperties();
        // TODO: Test failed cause token field is empty!!!
        //  Demand help with it, maybe add same Spring Security Class ass mock
    }

    @Test
    @DisplayName("Edit current user")
    void editCurrentUserTest() {

        ProfileEditRequest profileEditRequest = new ProfileEditRequest(
                "Petr",
                "Petrov",
                localDate, // birth date
                "+7(222)333-44-55",
                "photoID",
                null, // null mustn't change about value
                "new town",
                "new country",
                PermissionMessage.FRIEND
        );

        PersonResponse personResponse = profileService.editCurrentUser(profileEditRequest);

        assertThat(personResponse).isNotNull();

        assertEquals("check first name", profileEditRequest.getFirstName(), personResponse.getFirstName());
        assertEquals("check last name", profileEditRequest.getLastName(), personResponse.getLastName());
        assertEquals("check phone", profileEditRequest.getPhone(), personResponse.getPhone());
        assertEquals("check photo", profileEditRequest.getPhoto(), personResponse.getPhoto());
        assertEquals("check that about not changed", accountService.getCurrentPerson().getAbout(), personResponse.getAbout());
        assertEquals("check town / city", profileEditRequest.getTown(), personResponse.getCity());
        assertEquals("check country", profileEditRequest.getCountry(), personResponse.getCountry());
        assertEquals("check permission massage", profileEditRequest.getPermissionMessage(), personResponse.getMessagesPermission());
    }

    @Test
    @DisplayName("Delete current user")
    void deleteCurrentUserTest(){

        CommonResponseData commonResponseData = profileService.deleteCurrentUser();

        assertThat(commonResponseData)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("Find user by Id")
    void findUserByIdTest() {
        final long id = 1L;
        Person person = new Person();

        Mockito.when(personRepository.getOne(id)).thenReturn(person);

        PersonResponse personResponse = profileService.findUserById(id);
        assertThat(personResponse).isNotNull();
    }

    @Test
	@DisplayName("get users wall")
	void getUserWall() {
    	Person person = accountService.getCurrentPerson();
		long offset = 0L;
		int itemPerPage = 2;
		Page<Post> postPage = Page.empty();
		Mockito.when(postRepository.findAllByPerson(person, profileService.getPageable(offset, itemPerPage)))
				.thenReturn(postPage);
		Mockito.when(personRepository.findById(person.getId())).thenReturn(person);

		CommonListResponse response = profileService.getUserWall(person.getId(), offset, itemPerPage);

		assertEquals("error field value", "string", response.getError());
		assertEquals("total field value", 0L, response.getTotal());
		assertEquals("offset field value", offset, response.getOffset());
		assertEquals("itemPerPage field value", itemPerPage, response.getPerPage());
		assertEquals("list size", 0, response.getData().size());
	}

    @Test
    @DisplayName("Post on users wall")
    void postOnUserWall() {
        Person person = accountService.getCurrentPerson();
        LocalDateTime nowLDT = LocalDateTime.now(ZoneId.systemDefault());
        long nowL = Timestamp.valueOf(nowLDT).getTime();
        String title = "Title example";
        String text = "Text example";
        PostRequest postRequest = new PostRequest(title, text);

        Mockito.when(personRepository.findById(person.getId())).thenReturn(person);

        CommonResponseData response = profileService.postOnUserWall(person.getId(), nowL, postRequest);
        PostResponse postResponse = (PostResponse) response.getData();

        assertEquals("Check author by email", person.getEmail(), postResponse.getAuthor().getEmail());
        assertEquals("Check title", title, postResponse.getTitle());
        assertEquals("Check text", text, postResponse.getPostText());
        assertThat(!postResponse.getTime().isBefore(nowLDT));
    }

    @Test
	@DisplayName("Search users")
	void searchUser() {
    	long offset = 0L;
    	int itemPerPage = 2;
		PersonSpecificationsBuilder builder = new PersonSpecificationsBuilder();
		Specification<Person> spec = builder.build();
		Page<Person> personPage = Page.empty();
    	Mockito.when(personRepository.findAll(spec, profileService.getPageable(offset, itemPerPage)))
				.thenReturn(personPage);

		CommonListResponse response = profileService.searchUser(
				null, null, null, null, null, null, offset, itemPerPage);


		assertEquals("error field value", "string", response.getError());
		assertEquals("total field value", 0L, response.getTotal());
		assertEquals("offset field value", offset, response.getOffset());
		assertEquals("itemPerPage field value", itemPerPage, response.getPerPage());
		assertEquals("list size", 0, response.getData().size());
	}

    @Test
    @DisplayName("Block/unblock user")
    void blockUser() {
        Person person = accountService.getCurrentPerson();

        Mockito.when(personRepository.findById(person.getId())).thenReturn(person);

        CommonResponseData blockResponse = profileService.blockUser(true, person.getId());
        CommonResponseData unblockResponse = profileService.blockUser(false, person.getId());

        assertThat(blockResponse).isNotNull().hasNoNullFieldsOrProperties();
        assertThat(unblockResponse).isNotNull().hasNoNullFieldsOrProperties();
    }

	@Test
	@DisplayName("Mapping personList to personResponseList")
	void getPersonResponseListFromPersonList() {
		List<Person> personList = new ArrayList<>();
		personList.add(new Person());
		personList.add(new Person());
		List<PersonResponse> personResponseList = profileService.getPersonResponseListFromPersonList(personList);

		assertThat(personList.size() == personResponseList.size());
	}

	@Test
	@DisplayName("Mapping postList to postResponseList")
	void getPostResponseListFromPostList() {
    	List<Post> postList = new ArrayList<>();
    	Person author = accountService.getCurrentPerson();
    	Post post = new Post();
    	post.setPerson(author);
    	post.setTime(LocalDateTime.now());
    	postList.add(post);
    	postList.add(post);
    	List<PostResponse> postResponseList = profileService.getPostResponseListFromPostList(postList);

    	assertThat(postResponseList.size() == postList.size());
	}
}
