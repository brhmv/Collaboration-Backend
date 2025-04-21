package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.dto.response.GlobalErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static az.edu.turing.turingcollab.model.enums.ErrorCode.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<GlobalErrorResponse> handleBaseException(BaseException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GlobalErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(AppNotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleAppNotFoundException(AppNotFoundException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<GlobalErrorResponse> handleInvalidDateFormatException(InvalidDateFormatException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(MentoriumAlreadyExistsException.class)
    public ResponseEntity<GlobalErrorResponse> handleMentoriumAlreadyExistsException(MentoriumAlreadyExistsException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(MentoriumNotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleMentoriumNotFoundException(MentoriumNotFoundException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(ProjectAlreadyExistsException.class)
    public ResponseEntity<GlobalErrorResponse> handleProjectAlreadyExistsException(ProjectAlreadyExistsException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleProjectNotFoundException(ProjectNotFoundException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        addErrorLog(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GlobalErrorResponse.builder()
                        .errorCode(BAD_REQUEST)
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        addErrorLog(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GlobalErrorResponse.builder()
                        .errorCode(BAD_REQUEST)
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        addErrorLog(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GlobalErrorResponse.builder()
                        .errorCode(BAD_REQUEST)
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, String exceptionType) {
        int statusCode = (httpStatus != null) ? httpStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        log.error("HTTP Status: {} | Error Message: {} | Exception Type: {}", statusCode, errorMessage, exceptionType);
    }
}
