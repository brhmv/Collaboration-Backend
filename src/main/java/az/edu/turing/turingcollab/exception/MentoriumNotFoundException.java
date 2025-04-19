package az.edu.turing.turingcollab.exception;

import az.edu.turing.turingcollab.model.enums.ErrorCode;

public class MentoriumNotFoundException extends BaseException {

    public MentoriumNotFoundException(Long mentoriumId) {
        super("Mentorium with ID " + mentoriumId + " not found", ErrorCode.MENTORIUM_NOT_FOUND);
    }
}
