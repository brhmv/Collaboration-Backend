package az.edu.turing.turingcollab.model.dto.response;

import az.edu.turing.turingcollab.model.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalErrorResponse implements Serializable {

    private UUID requestId;
    private ErrorCode errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;
}
