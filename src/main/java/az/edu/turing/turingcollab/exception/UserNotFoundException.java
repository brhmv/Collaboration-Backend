package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(Long userId) {
        super("User with ID " + userId + " not found", ErrorCode.USER_NOT_FOUND);
    }
}
