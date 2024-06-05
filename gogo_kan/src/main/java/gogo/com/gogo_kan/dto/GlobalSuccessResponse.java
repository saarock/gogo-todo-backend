package gogo.com.gogo_kan.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSuccessResponse<T> {
    private int status;
    private String type;
    private String message;
    private T data;
}
