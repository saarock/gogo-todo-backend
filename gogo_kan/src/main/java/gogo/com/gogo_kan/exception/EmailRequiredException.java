package gogo.com.gogo_kan.exception;

public class EmailRequiredException extends RuntimeException {
    public EmailRequiredException(String msg) {
        super(msg);
    }
}
