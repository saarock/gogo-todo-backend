package gogo.com.gogo_kan.exception;

import gogo.com.gogo_kan.service.UserService;

public class UserServiceException extends RuntimeException {
    public UserServiceException (String message) {
        super(message);
    }
}
