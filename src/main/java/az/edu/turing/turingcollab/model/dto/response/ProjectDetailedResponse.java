package az.edu.turing.turingcollab.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailedResponse {

    private Long id;
    private String name;
    private String applicationDeadline;
    private String imageName;
    private String description;
    private String requirements;
    private String startDate;
    private String endDate;
    private String file;
    private String link;
    private String fields;
    private Set<UserCardResponse> participants;
}
