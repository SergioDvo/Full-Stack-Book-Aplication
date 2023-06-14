package es.metrica.ddtbookstore.endPoint.errores;

import es.metrica.ddtbookstore.services.exceptions.EntityAlreadyExistException;
import es.metrica.ddtbookstore.services.exceptions.NullDataException;
import es.metrica.ddtbookstore.services.exceptions.CustomWrongArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Component
public class ErrorController extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomWrongArgumentException.class)
    public ResponseEntity<ApiError> handleWrongArgumentException(CustomWrongArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND.value()));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullDataException.class)
    public ResponseEntity<ApiError> handleNullDataException(NullDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value()));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ApiError> handleEntityAlreadyExistException(EntityAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value()));

    }
}
