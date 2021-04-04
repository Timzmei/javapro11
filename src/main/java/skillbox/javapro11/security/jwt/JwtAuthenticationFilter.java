package skillbox.javapro11.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import skillbox.javapro11.api.request.AuthRequest;
import skillbox.javapro11.api.response.PersonDTO;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authManager;
    private JwtTokenProvider jwtTokenProvider;
    private PersonRepository personRepository;


    public JwtAuthenticationFilter(AuthenticationManager authManager,
                                   JwtTokenProvider jwtTokenProvider,
                                   PersonRepository personRepository) {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.personRepository = personRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequest authRequest = getCredentials(request);
            return authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AuthRequest getCredentials(HttpServletRequest request) {
        AuthRequest auth = null;
        try {
            auth = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) {
        try {
            SecurityContextHolder.getContext().setAuthentication(auth);
            PersonDTO personResponse = createPersonResponse(auth.getName());

            Gson gson = new Gson();
            response.getWriter().print(gson.toJson(personResponse));
            response.getWriter().flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PersonDTO createPersonResponse(String email) {
        Person person = personRepository.findByEmail(email);
        String token = jwtTokenProvider.createToken(person);

        PersonDTO personResponse = new PersonDTO();
        personResponse.setId(person.getId());
        personResponse.setFirstName(person.getFirstName());
        personResponse.setLastName(person.getLastName());
        //personResponse.setRegistrationDate(person.getRegistrationDate());
        //personResponse.setBirthDate(person.getBirthday());
        personResponse.setEmail(person.getEmail());
        personResponse.setPhone(person.getPhone());
        personResponse.setPhoto(person.getPhoto());
        personResponse.setAbout(person.getAbout());

        /*LocationOrLanguageDTO locationOrLanguageDTO = new LocationOrLanguageDTO();
        personResponse.setCity(locationOrLanguageDTO);*/
        personResponse.setMessagesPermission(person.getPermissionMessage());
        //personResponse.setLastOnlineTime(person.getLastTimeOnline());
        personResponse.setBlocked(person.isBlocked());
        personResponse.setToken(token);

        return personResponse;
    }
}
