package az.edu.turing.turingcollab.model.dto.response;

import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SentAppResponse {

    private Long id;
    private String topic;
    private ApplicationStatus status;
    private String createdAt;
}
