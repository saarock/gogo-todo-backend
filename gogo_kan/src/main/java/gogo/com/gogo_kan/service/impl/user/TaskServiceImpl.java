package gogo.com.gogo_kan.service.impl.user;

import gogo.com.gogo_kan.dto.request.TaskUpdateRequest;
import gogo.com.gogo_kan.exception.TaskException;
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

    @Override
    public boolean deletedTask(int id) {
        try {
            taskRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Task updateTask(TaskUpdateRequest taskUpdateRequest, int taskId) {

        Task task = taskRepository.getReferenceById(taskId);
        if (task == null) {
            throw new TaskException("Task not found exception");
        }
        if (!taskUpdateRequest.getTaskTitle().isEmpty()) {
            task.setName(taskUpdateRequest.getTaskTitle());
        }
        if (!taskUpdateRequest.getTaskContent().isEmpty()) {

            task.setContent(taskUpdateRequest.getTaskContent());

        }

        System.out.println(taskUpdateRequest.getTaskContent() + "   **** this is haha ");
        taskRepository.save(task);
        return task;
    }

}
