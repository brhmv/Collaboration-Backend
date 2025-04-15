package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class ProjectNotFoundException extends BaseException {

    public ProjectNotFoundException(Long projectId) {
        super("Project with ID " + projectId + " not found", ErrorCode.PROJECT_NOT_FOUND);
    }
}
