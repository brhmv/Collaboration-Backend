package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class IllegalArgumentException extends BaseException {

    public IllegalArgumentException(String message) {
        super(message, ErrorCode.BAD_REQUEST);
    }
}
