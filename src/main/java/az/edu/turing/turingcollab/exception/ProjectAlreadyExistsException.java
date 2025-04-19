package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class ProjectAlreadyExistsException extends BaseException {

    public ProjectAlreadyExistsException(Long id) {
        super("Project with ID: " + id + "already exists", ErrorCode.PROJECT_ALREADY_EXISTS);
    }
}
