package gogo.com.gogo_kan.dto.response;


import gogo.com.gogo_kan.helper.User;
import gogo.com.gogo_kan.helper.Tokens;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String type;
    private HttpStatus status;
    private String message;
    private User user;
    private Tokens tokens;
}
