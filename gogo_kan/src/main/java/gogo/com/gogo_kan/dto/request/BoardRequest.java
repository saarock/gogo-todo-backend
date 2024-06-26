package gogo.com.gogo_kan.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequest {
    private int projectId;
    private String name;
    private int projectIndex;
    private int boardIndex;
}
