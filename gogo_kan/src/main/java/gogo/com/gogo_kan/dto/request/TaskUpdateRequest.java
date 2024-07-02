package gogo.com.gogo_kan.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {
    private String taskTitle;
    private String taskContent;
}
