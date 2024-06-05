package gogo.com.gogo_kan.services.impl.user;


import gogo.com.gogo_kan.models.User;
import gogo.com.gogo_kan.repo.UserRepository;
import gogo.com.gogo_kan.utils.UserDetailsCache;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomeUserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepo;
    @Autowired
    private UserDetailsCache userDetailsCache;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user != null) {
            userDetailsCache.addToCache(user);
          

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    new ArrayList<>() // Add user roles or authorities here
            );
        }
        throw new UsernameNotFoundException("User not Found");
    }
}
