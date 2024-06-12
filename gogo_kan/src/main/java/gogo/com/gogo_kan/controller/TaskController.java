package gogo.com.gogo_kan.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @PostMapping("/create-task")
    public Object createTask() {
        int a = 12;
        return null;
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
