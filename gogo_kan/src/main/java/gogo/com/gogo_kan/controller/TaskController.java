package gogo.com.gogo_kan.controller;

import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.TaskRequest;
import gogo.com.gogo_kan.dto.request.TaskUpdateRequest;
import gogo.com.gogo_kan.exception.BoardNotFoundException;
import gogo.com.gogo_kan.exception.TaskException;
import gogo.com.gogo_kan.model.Board;
import gogo.com.gogo_kan.model.Task;
import gogo.com.gogo_kan.service.BoardService;
import gogo.com.gogo_kan.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private BoardService boardService;
    @PostMapping("/create-task")
    public Object createTask(@RequestBody TaskRequest taskRequest) throws Exception  {

        Board board = boardService.findBoardById(taskRequest.getBoardId());
        if (board == null) {
            throw new BoardNotFoundException("Board not found");
        }

        Task task = new Task();
        task.setBoard(board);
        task.setName(taskRequest.getName());
        task.setContent(taskRequest.getContent());
        task.setIndex(taskRequest.getIndex());
        task.setBoardIndex(taskRequest.getBoardIndex());
        Task savedTask = taskService.createNewTask(task);

        if (savedTask == null) {
            throw new Exception("Task cannot created");
        }
        return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Task created Successfully", savedTask);


    }


    @PutMapping("/update-task/{id}")
    public Object updateTask(@PathVariable int id, @RequestBody TaskUpdateRequest taskUpdateRequest) {
        System.out.println("haha");
        if (taskUpdateRequest == null) {
            throw new TaskException("Task updated is required in the proper manner");
        }

        Task task = taskService.updateTask(taskUpdateRequest, id);
        if (task == null) throw new TaskException("Cannot get the updated task");
        System.out.println(task.getName() + "   this is the task name");

        return new GlobalSuccessResponse<>(
                HttpStatus.OK,
                "success",
                "Task updated Successfully",
                task);

    }



    @DeleteMapping("/delete-task/{id}")
    public Object deleteTask(@PathVariable int id) {
         boolean isDeleted = taskService.deletedTask(id);
         if (!isDeleted) throw new TaskException("Cannot deleted the task try again");
         return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Task deleted Successfully", null);

    }

}
