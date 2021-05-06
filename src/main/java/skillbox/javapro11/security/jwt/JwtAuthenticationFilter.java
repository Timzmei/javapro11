package skillbox.javapro11.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import skillbox.javapro11.api.request.AuthRequest;
import skillbox.javapro11.api.response.CommonResponseData;
import skillbox.javapro11.api.response.PersonResponse;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PersonRepository personRepository;

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
            Person person = personRepository.findByEmail(auth.getName());
            String token = jwtTokenProvider.createToken(person);
            response.addHeader(JwtParam.AUTHORIZATION_HEADER_STRING, token);

            CommonResponseData commonResponseData = new CommonResponseData();
            commonResponseData.setError("string");
            commonResponseData.setTimestamp(LocalDateTime.now());
            commonResponseData.setData(PersonResponse.fromPerson(person, token));

            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().print(objectMapper.writeValueAsString(commonResponseData));
            response.getWriter().flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
