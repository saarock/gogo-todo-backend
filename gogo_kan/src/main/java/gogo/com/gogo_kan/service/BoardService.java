package gogo.com.gogo_kan.service;

import gogo.com.gogo_kan.model.Board;

public interface BoardService {
    public Board createNewBoard(Board baord);
    public Board findBoardById(int id);
    public boolean deleteBoard(int boardId);
    public Board updateBoard(int boardId,String newBoardName);
    public boolean compeleteOrNot(int id, boolean isCompelete);

}
