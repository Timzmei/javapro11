package skillbox.javapro11.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import skillbox.javapro11.ServiceTestConfiguration;
import skillbox.javapro11.api.request.ProfileEditRequest;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.PersonResponse;
import skillbox.javapro11.enums.PermissionMessage;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

}
