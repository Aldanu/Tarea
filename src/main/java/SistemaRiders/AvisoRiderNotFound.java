package SistemaRiders;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AvisoRiderNotFound {
    @ResponseBody
    @ExceptionHandler(RiderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String AvisoRiderNotFoundHandler(RiderNotFoundException ex) {
        return ex.getMessage();
    }
}
