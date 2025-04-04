package az.edu.turing.turingcollab.domain.entity;

import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApplicationEntity extends BaseEntity {

    private Integer projectId;
    private ApplicationStatus status;
}
