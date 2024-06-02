package gogo.com.gogo_kan.services.impl.user;


import gogo.com.gogo_kan.dto.UserRepository;
import gogo.com.gogo_kan.models.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomeUserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ( UserDetails) this.userRepo.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
