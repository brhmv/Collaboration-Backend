package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.ProjectApplicationEntity;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ProjectAppRepository extends JpaRepository<ProjectApplicationEntity, Long> {

    List<ProjectApplicationEntity> findAllByStatusIsAndProject_CreatedBy(ApplicationStatus status, Long createdBy);

    List<ProjectApplicationEntity> findAllByStatusIsAndCreatedBy(ApplicationStatus status, Long createdBy);

    void deleteAllByStatusAndUpdatedAtBetween(ApplicationStatus status, Instant start, Instant end);
}
