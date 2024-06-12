package gogo.com.gogo_kan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    private String name;
//    private String id;
    private Instant createdAt;
    private Instant updatedAt;
    private int userId;
}
