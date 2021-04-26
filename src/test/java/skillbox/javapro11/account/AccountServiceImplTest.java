package skillbox.javapro11.account;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import skillbox.javapro11.api.request.RegisterRequest;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.security.jwt.JwtTokenProvider;
import skillbox.javapro11.security.userdetails.UserDetailsServiceImpl;
import skillbox.javapro11.service.AccountService;
import skillbox.javapro11.service.EmailSenderService;
import skillbox.javapro11.service.impl.AccountServiceImpl;
import skillbox.javapro11.service.impl.EmailServiceImpl;
import skillbox.javapro11.service.impl.PersonServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(AccountService.class)
@WithMockUser
public class AccountServiceImplTest {
    @MockBean
    AccountServiceImpl accountService;

    @MockBean
    private PersonServiceImpl personServiceImpl;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    private EmailServiceImpl emailService;

    @MockBean
    private EmailSenderService emailSenderService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    public void testFindPersonByEmail(){
        RegisterRequest registerRequest = new RegisterRequest("mymail@mail.ru",
                "password", "password", "Petr", "Petrov", "123456");
        Person newPerson = new Person(registerRequest);

        Person savedPerson = new Person("Petr", "password", "mymail@mail.ru");

        Mockito.when(personRepository.findByEmail(savedPerson.getEmail())).thenReturn(newPerson);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getEmail()).isEqualTo(newPerson.getEmail());
    }

    @Test
    public void testAddNewPerson(){
        RegisterRequest registerRequest = new RegisterRequest("mymail@mail.ru",
                "password", "password", "Petr", "Petrov", "123456");
        Person newPerson = new Person(registerRequest);
        Person savedPerson = new Person("Petr", "password", "mymail@mail.ru");

        Mockito.when(personServiceImpl.add(registerRequest)).thenReturn(newPerson);

        assertThat(savedPerson).isNotNull();
        assertThat(savedPerson.getEmail()).isEqualTo(newPerson.getEmail());
    }

    @Test
    public void testChangePassword(){
        String email = "my@mail.ru";
        String password = "password";
        String passwordNew = "passwordNew";

        Person savedPerson = new Person("Petr", password, email);
        savedPerson.setPassword(passwordNew);

        Mockito.when(personRepository.findByPassword(savedPerson.getPassword())).thenReturn(savedPerson);
        Person foundPerson = personRepository.findByPassword(passwordNew);

        assertThat(foundPerson.getFirstName()).isEqualTo("Petr");
    }

    @Test
    public void testChangeEmail(){
        Person savedPerson = new Person("Petr", "password", "first@mail.ru");
        savedPerson.setEmail("second@mail.ru");
        Mockito.when(personRepository.findByEmail(savedPerson.getEmail())).thenReturn(savedPerson);
        Person foundPerson = personRepository.findByEmail("second@mail.ru");

        assertThat(foundPerson.getFirstName()).isEqualTo("Petr");
    }
}
