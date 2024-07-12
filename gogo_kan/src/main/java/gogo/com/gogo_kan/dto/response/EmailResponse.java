package gogo.com.gogo_kan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponse {
    private HttpStatus status;
    private String type;
    private String message;
    private String email;
}
