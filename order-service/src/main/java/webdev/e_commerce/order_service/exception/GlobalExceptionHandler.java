package webdev.e_commerce.order_service.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import webdev.e_commerce.common.APIResponse;
import webdev.e_commerce.common.exceptions.ErrorMessages;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static webdev.e_commerce.common.api.APIHelperUtil.createUnifiedResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String DEFAULT_INTERNAL_SERVER_ERROR = ErrorMessages.DEFAULT_INTERNAL_SERVER_ERROR;

    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<APIResponse> internalExceptionHandler(Exception ex) {
        log.error(ex.getLocalizedMessage(), ex);
        return createUnifiedResponse(null, HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> validationExceptionHandler(MethodArgumentNotValidException ex) {

        StringBuilder errorsString = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(p -> errorsString.append(System.lineSeparator()));
        return createUnifiedResponse(null, HttpStatus.BAD_REQUEST, errorsString.toString());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<APIResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        log.error("Method not allowed With Exception : ", ex);
        return createUnifiedResponse(null, BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<APIResponse> handleInvalidFormatException(InvalidFormatException ex) {
        log.error("Invalid Format Exception :  ", ex);
        return createUnifiedResponse(null, BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse> productNotFoundExceptionHandler(EntityNotFoundException ex) {
        log.error(ex.getLocalizedMessage(), ex);
        return createUnifiedResponse(null, HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
