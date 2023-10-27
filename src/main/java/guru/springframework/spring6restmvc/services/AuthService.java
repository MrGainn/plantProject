package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String userName;
        String password;
        try {
            userName = userRepository.findByUsername(username).getUsername();
            password = userRepository.findByUsername(username).getHashedpassword();
        }
        catch (InternalAuthenticationServiceException e){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }


        if (userName == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        UserDetails userDetails = User.withUsername(userName)
                .password(passwordEncoder().encode(password))
                .roles("USER")
                .build();

        return userDetails;
    }

}
