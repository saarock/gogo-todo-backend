package gogo.com.gogo_kan.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSuccessResponse<T> {
    private HttpStatus status;
    private String type;
    private String message;
    private T data;
}
