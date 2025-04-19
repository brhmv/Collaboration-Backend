package az.edu.turing.turingcollab.model.dto.response;

import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCardResponse {

    private Long id;
    private String imageName;
    private String name;
    private String shortDescription;
    private UserCardResponse creator;
    private String applicationDeadline;
    private ProjectStatus status;
}
