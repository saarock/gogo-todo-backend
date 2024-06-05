package gogo.com.gogo_kan.dto.request;

import gogo.com.gogo_kan.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private User user;
    private long otp;
}
