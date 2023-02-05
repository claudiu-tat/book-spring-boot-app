package ro.sda.book_app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.sda.book_app.model.ClientError;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ClientError> handleNotFoundEx(NotFoundException ex) {
        log.error("Exception occurred with message: {}", ex.getMessage(), ex);

        ClientError response = ClientError.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Not found in the database.")
                .errors(Collections.singletonList(ex.getLocalizedMessage()))
                .timeStamp(new Date(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ClientError> handleValidationEx(MethodArgumentNotValidException ex) {
        log.error("Validation exception occurred: {}", ex.getMessage());

        // classic way to iterate
/*        List<FieldError> errorList = ex.getFieldErrors();
        List<String> resultList = new ArrayList<>();
        for (FieldError err : errorList) {
            resultList.add(err.getDefaultMessage());
        }*/

        // stream api - functional of the java language
        List<String> list = ex.getFieldErrors().stream().map((FieldError e) -> e.getDefaultMessage()).toList();

        ClientError response = ClientError.builder()
                .statusCode(400)
                .message("Constraint violation")
                .errors(list)
                .timeStamp(new Date(System.currentTimeMillis()))
                .build();
        return new ResponseEntity<ClientError>(response, HttpStatus.BAD_REQUEST);
    }
}
