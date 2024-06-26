package gogo.com.gogo_kan.dto.response;

import gogo.com.gogo_kan.model.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private List<Board> boards;
    private Integer index;
    private String createdAt;
    private String updatedAt;
    private String userId;
}