package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class AppNotFoundException extends BaseException {

    public AppNotFoundException(Long id) {
        super("Application with ID " + id + " not found", ErrorCode.APP_NOT_FOUND);
    }
}
