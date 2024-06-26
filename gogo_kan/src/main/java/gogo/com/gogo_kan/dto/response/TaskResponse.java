package gogo.com.gogo_kan.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private String name;
    private String content;
    private int index;
    private int boardId;
    private int boardIndex;
    private Instant createdAt;
    private Instant updatedAt;
    private int id;
}
