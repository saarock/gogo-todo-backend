package gogo.com.gogo_kan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String name;
    private String content;
    private int index;
    private int boardId;
    private int boardIndex;
}
