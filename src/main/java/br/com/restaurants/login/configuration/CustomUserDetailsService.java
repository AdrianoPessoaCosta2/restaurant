package br.com.restaurants.login.configuration;

import br.com.restaurants.database.adapter.UserPersistence;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserPersistence userPersistence;

    public CustomUserDetailsService(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String password = userPersistence.findPasswordByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials" + login));

        return org.springframework.security.core.userdetails.User
                .withUsername(login)
                .password(password)
                .authorities("ROLE_ADM")
                .build();
    }
}
