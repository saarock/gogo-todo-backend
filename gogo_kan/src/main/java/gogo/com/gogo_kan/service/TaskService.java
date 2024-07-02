package gogo.com.gogo_kan.service;

import gogo.com.gogo_kan.dto.request.TaskUpdateRequest;
import gogo.com.gogo_kan.model.Task;

public interface TaskService {
    public Task createNewTask(Task task);
    public boolean deletedTask(int id);
    public Task updateTask(TaskUpdateRequest taskUpdateRequest, int taskId);

}
