package gogo.com.gogo_kan.controller;


import gogo.com.gogo_kan.dto.GlobalSuccessResponse;
import gogo.com.gogo_kan.dto.request.BoardRequest;
import gogo.com.gogo_kan.dto.request.BoardUpdateRequestWrapper;
import gogo.com.gogo_kan.dto.request.IsCompleted;
import gogo.com.gogo_kan.dto.response.BoardResponse;
import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.exception.*;
import gogo.com.gogo_kan.model.Board;
import gogo.com.gogo_kan.model.Product;
import gogo.com.gogo_kan.model.Task;
import gogo.com.gogo_kan.service.BoardService;
import gogo.com.gogo_kan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;


@RestController
public class BoardController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BoardService boardService;

    @PostMapping("/create-board")
    public Object createBoard(@RequestBody BoardRequest boardRequest) {
        if (boardRequest == null) {
            throw new BoardNotFoundException("Board details should be required in manner");
        }
        int projectId = boardRequest.getProjectId();

        Product product = productService.findProductByProductId(projectId);
        if (product == null) {
            throw new ProjectNameNotFoundException("Project Doesn't exist");
        }
        // search here also with the project id and the board name
        Board board = new Board();
        board.setName(boardRequest.getName());
        board.setProjectIndex(boardRequest.getProjectIndex());
        board.setBoardIndex(boardRequest.getBoardIndex());
        board.setBoardProject(product);
        try {
            Board savedBoard = boardService.createNewBoard(board);
            if (savedBoard == null) {
                throw new Exception("Board doesn't saved");
            }
            BoardResponse boardResponse = new BoardResponse();
            boardResponse.setName(savedBoard.getName());
            boardResponse.setBoardId(savedBoard.getBoardId());
            boardResponse.setProjectIndex(savedBoard.getProjectIndex());
            boardResponse.setUpdatedAt(savedBoard.getUpdatedAt());
            boardResponse.setComplete(savedBoard.isComplete());
            List<Task> userTasks = savedBoard.getTasks();
            if (userTasks == null) {
                boardResponse.setTasks(Collections.emptyList());
            } else {
                boardResponse.setTasks(userTasks);
            }
//            boardResponse.set
            return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Board created successfully", boardResponse);


        } catch (Exception e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST, "error", e.getMessage());

        }


    }

    @DeleteMapping("/delete-board/{id}")
    public Object deleteBoard(@PathVariable int id) {
        if (id == -1) {
            throw new BoardIdNotFoundException("Board Id not found Exception");
        }
        boolean isDeleted = boardService.deleteBoard(id);
        if (!isDeleted) {
            throw new BoardException("Cannot deleted the board");
        }

        return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Board Deleted SuccessFully", null);

    }


    @PutMapping("/update-board/{id}")
    public Object updateBoard(@PathVariable int id, @RequestBody BoardUpdateRequestWrapper boardRequestWrapper) {
        if (id == -1 || id == 0) {
            throw new BoardIdNotFoundException("Board Id not found Exception");
        }
        String boardName = boardRequestWrapper.getBoardName();
        if (boardName == null) {
            throw new BoardNameNotFoundException("Board details not found");
        }
        if (boardName.isEmpty()) {
            throw new BoardNameNotFoundException("Board Name Not Found");
        }
        Board board = boardService.updateBoard(id, boardName);
        if (board == null) {
            throw new BoardException("Cannot updated the board");
        }
        return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Board Updated SuccessFully", board);
    }


    @PutMapping("/check-complete-or-not/{id}")
    public Object checkCompleteOrNotComplete(@PathVariable int id, @RequestBody IsCompleted isComplete) {
        if (id == -1 || id == 0) {
            throw new BoardIdNotFoundException("Board Id not found Exception");
        }
        boolean isCompleted = this.boardService.compeleteOrNot(id, isComplete.isCompleted);
        return new GlobalSuccessResponse<>(HttpStatus.OK, "success", "Board Updated SuccessFully", isCompleted);
    }
}
