package gogo.com.gogo_kan.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmailRequest {
    private EmailRequest emailRequest;
    private Long otp;
}
