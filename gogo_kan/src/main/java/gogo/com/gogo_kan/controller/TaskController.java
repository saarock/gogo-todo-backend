package gogo.com.gogo_kan.controller;

import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.TaskRequest;
import gogo.com.gogo_kan.dto.response.TaskResponse;
import gogo.com.gogo_kan.exception.BoardNotFoundException;
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
    public Object updateTask(@PathVariable long id) {
        return null;
    }


    @DeleteMapping("delete-task/{id}")
    public Object deleteTask(@PathVariable long id) {
        return null;
    }

}