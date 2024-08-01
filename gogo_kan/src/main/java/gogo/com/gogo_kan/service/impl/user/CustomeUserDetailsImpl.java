package gogo.com.gogo_kan.service.impl.user;


import gogo.com.gogo_kan.exception.CredentialException;
import gogo.com.gogo_kan.model.User;
import gogo.com.gogo_kan.repo.UserRepository;
import gogo.com.gogo_kan.util.UserDetailsCacheUtility;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@AllArgsConstructor
public class CustomeUserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepo;
    private UserDetailsCacheUtility userDetailsCache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepo.findByEmail(username);
            if (user != null) {
                userDetailsCache.addToCache(user);
                return new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        new ArrayList<>()
                );
            }
            throw new CredentialException("Credentials doesn't match");
        } catch (Error eror) {
            throw new CredentialException("Credential doesn't match exception.");
        }
    }
}
