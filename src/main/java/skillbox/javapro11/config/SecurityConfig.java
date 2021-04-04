package skillbox.javapro11.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import skillbox.javapro11.repository.PersonRepository;
import skillbox.javapro11.security.handler.LogoutSuccessHandlerImpl;
import skillbox.javapro11.security.jwt.JwtAuthenticationFilter;
import skillbox.javapro11.security.jwt.JwtTokenFilter;
import skillbox.javapro11.security.jwt.JwtTokenProvider;
import skillbox.javapro11.security.userdetails.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private PersonRepository personRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                //SessionCreationPolicy.STATELESS сессию хранить не нужно, так как по токену
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //authorizeRequests() - все запросы через spring security
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/logout"))
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl(jwtTokenProvider))
                .and()
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationFilter customUsernamePasswordAuthenticationFilter()
            throws Exception {
        JwtAuthenticationFilter customUsernamePasswordAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider, personRepository);
        customUsernamePasswordAuthenticationFilter
                .setAuthenticationManager(authenticationManagerBean());
        customUsernamePasswordAuthenticationFilter
                .setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/login", "POST"));
        return customUsernamePasswordAuthenticationFilter;
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}