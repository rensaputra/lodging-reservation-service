package au.id.res.lodging.lodging_reservation_service.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*Whenever this controller method succeeds—or whenever this custom exception is thrown.
Set the HTTP response status code to 400 Bad Request instead of the default 200 OK (or 500 Internal Server Error).*/

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
