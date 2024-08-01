package gogo.com.gogo_kan.service.impl.user;

import gogo.com.gogo_kan.exception.BoardException;
import gogo.com.gogo_kan.exception.BoardNotFoundException;
import gogo.com.gogo_kan.model.Board;
import gogo.com.gogo_kan.repo.BoardRepository;
import gogo.com.gogo_kan.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService  {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Board createNewBoard(Board board) {
        return boardRepository.save(board);

    }

    @Override
    public Board findBoardById(int id) {
        return boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("Board not found"));

    }
    @Override
    public boolean deleteBoard(int boardId) {
        try {
            boardRepository.deleteById(boardId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Board updateBoard(int boardId, String newBoardName) {
        try {
            Optional<Board> board = this.boardRepository.findById(boardId);
            if (board.isEmpty()) {
                return null;
            }
            Board currentBoard = board.get();
            currentBoard.setName(newBoardName);
            return this.boardRepository.save(currentBoard);


        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public boolean compeleteOrNot(int id, boolean isCompelete) {
        try {
            Board board = this.boardRepository.getReferenceById(id);
            board.setComplete(isCompelete);
            this.boardRepository.save(board);
            return board.isComplete();
        } catch (Exception error) {
            throw new BoardException("Something wrong");
        }
    }
}
