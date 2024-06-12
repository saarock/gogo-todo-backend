package gogo.com.gogo_kan.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BoardController {

    @PostMapping("/create-board")
    public Object createBoard () {
        return null;
    }

    @PutMapping("/delete-board/{id}")
    public Object deleteBoard(@PathVariable long id) {
        return null;
    }

    @PostMapping("/update-board/{id}")
    public Object updateBoard(@PathVariable long id) {
        return null;
    }
}
