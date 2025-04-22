package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.ProjectApplicationEntity;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Repository
public interface ProjectAppRepository extends JpaRepository<ProjectApplicationEntity, Long> {

    List<ProjectApplicationEntity> findAllByStatusIsAndProject_CreatedBy(ApplicationStatus status, Long createdBy);

    List<ProjectApplicationEntity> findAllByCreatedBy(Long createdBy);

    void deleteAllByStatusInAndUpdatedAtBetween(Set<ApplicationStatus> statusSet, Instant start, Instant end);

    boolean existsByCreatedByAndProject_IdAndStatus(Long cratedBy, Long projectId, ApplicationStatus statuses);
}
