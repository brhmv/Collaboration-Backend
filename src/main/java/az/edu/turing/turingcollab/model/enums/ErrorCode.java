package az.edu.turing.turingcollab.model.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND),
    MENTORIUM_NOT_FOUND(HttpStatus.NOT_FOUND),
    MENTORIUM_ALREADY_EXISTS(HttpStatus.CONFLICT),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    PROJECT_ALREADY_EXISTS(HttpStatus.CONFLICT),
    APP_NOT_FOUND(HttpStatus.NOT_FOUND),
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

    ErrorCode(HttpStatus status) {
        this.httpStatus = status;
    }
}

