package gogo.com.gogo_kan.service.impl.user;

import gogo.com.gogo_kan.model.Task;
import gogo.com.gogo_kan.repo.TaskRepository;
import gogo.com.gogo_kan.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public Task createNewTask(Task task) {
        return this.taskRepository.save(task);
    }
}
