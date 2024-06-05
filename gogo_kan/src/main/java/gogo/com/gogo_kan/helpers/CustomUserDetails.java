package gogo.com.gogo_kan.helpers;

import gogo.com.gogo_kan.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails {
    private User user;
    private UserDetails userDetails;
}
