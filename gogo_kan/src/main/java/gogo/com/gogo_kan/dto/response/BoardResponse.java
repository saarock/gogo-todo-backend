package gogo.com.gogo_kan.dto.response;


import gogo.com.gogo_kan.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    private String projectName;
    private String name;
    private int boardId;
    private int projectIndex;
    private boolean complete;
//    private int boardIndex;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Task> tasks;

}
