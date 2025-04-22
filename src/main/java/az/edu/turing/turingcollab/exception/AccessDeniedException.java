package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class AccessDeniedException extends BaseException {

    public AccessDeniedException(String message) {
        super(message, ErrorCode.FORBIDDEN);
    }
}
