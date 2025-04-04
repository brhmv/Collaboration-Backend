package az.edu.turing.turingcollab.domain.entity;

import az.edu.turing.turingcollab.model.enums.TuringField;
import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity extends BaseEntity {

    private String name;
    private String description;
    private String shortDescription;
    private String requirements;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime applicationDeadline;
    private Map<TuringField, Integer> fields;
    private List<ApplicationEntity> applicationEntities;
    private String additionalLink;
    private String picture;
    private ProjectStatus status;
    private List<UserEntity> participants;
}
