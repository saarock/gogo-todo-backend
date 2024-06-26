package gogo.com.gogo_kan.advice;

import gogo.com.gogo_kan.dto.response.ErrorResponse;
import gogo.com.gogo_kan.exception.*;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {EmailAlreadyExistException.class,
            EmailRequiredException.class,
            FullNameRequiredException.class,
            InvalidOtpException.class,
            OtpExpiredException.class,
            OtpRequiredException.class,
            PasswordRequiredException.class,
            UserDetailsRequriedException.class,
            ProductNameRequired.class,
            ProductNameExist.class,
            BoardNotFoundException.class,
            BoardIdNotFoundException.class,
            BoardNameNotFoundException.class,
            ProjectIndexNotFoundException.class,
            BoardIndexNotFoundException.class,
            ProjectNameNotFoundException.class,
            PasswordException.class,
            EmailException.class,
            InvalidException.class,
            ProductException.class,
            BoardException.class
    })
    protected ResponseEntity<ErrorResponse> handelConflict(
            RuntimeException ex, WebRequest request
    ) {

        System.out.println("Error test ******************* ");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
