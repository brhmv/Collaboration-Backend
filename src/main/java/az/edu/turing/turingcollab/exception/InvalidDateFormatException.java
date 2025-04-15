package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class InvalidDateFormatException extends BaseException {

    public InvalidDateFormatException(String date) {
        super("Invalid date format: " + date, ErrorCode.BAD_REQUEST);
    }
}
