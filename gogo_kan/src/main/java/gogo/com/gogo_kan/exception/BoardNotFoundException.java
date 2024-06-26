package gogo.com.gogo_kan.exception;

public class BoardNotFoundException extends  RuntimeException{
    public BoardNotFoundException(String message) {
        super(message);
    }
}
