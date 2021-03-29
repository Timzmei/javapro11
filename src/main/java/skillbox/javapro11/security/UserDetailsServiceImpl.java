package skillbox.javapro11.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skillbox.javapro11.model.entity.Person;
import skillbox.javapro11.repository.PersonRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email);
        if (person == null) {
            throw new UsernameNotFoundException("Unknown user: " + email);
        }
        UserDetails userDetails = User.builder()
                .username(person.getEmail())
                .password(person.getPassword())
                .authorities("FULL")
                .build();

        return userDetails;
    }
}
