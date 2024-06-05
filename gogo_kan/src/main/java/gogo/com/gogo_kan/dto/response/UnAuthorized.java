package gogo.com.gogo_kan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UnAuthorized {
    private int status;
    private String type;
    private String message;
    private String path;
}
