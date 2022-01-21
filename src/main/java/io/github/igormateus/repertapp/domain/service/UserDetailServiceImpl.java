package io.github.igormateus.repertapp.domain.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.github.igormateus.repertapp.domain.model.User;
import io.github.igormateus.repertapp.domain.model.UserDetailImpl;
import io.github.igormateus.repertapp.domain.repository.UserRespository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> user = userRespository.findByLogin(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(
                    String.format("User '%s' not found", username));
        }

        return new UserDetailImpl(user);
    }

}
