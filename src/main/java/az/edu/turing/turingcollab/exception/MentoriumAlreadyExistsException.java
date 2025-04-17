package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class MentoriumAlreadyExistsException extends BaseException {

    public MentoriumAlreadyExistsException(Long userId) {
        super("Mentorium with ID " + userId + " already exists", ErrorCode.ALREADY_EXISTS);
    }
}
