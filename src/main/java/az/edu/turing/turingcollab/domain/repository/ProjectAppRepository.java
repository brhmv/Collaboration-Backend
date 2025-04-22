package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.ProjectApplicationEntity;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Set;

@Repository
public interface ProjectAppRepository extends JpaRepository<ProjectApplicationEntity, Long> {

    Page<ProjectApplicationEntity> findAllByStatusIsAndProject_CreatedBy(ApplicationStatus status, Long createdBy, Pageable pageable);

    Page<ProjectApplicationEntity> findAllByCreatedBy(Long createdBy, Pageable pageable);

    void deleteAllByStatusInAndUpdatedAtBetween(Set<ApplicationStatus> statusSet, Instant start, Instant end);

    boolean existsByCreatedByAndProject_IdAndStatus(Long cratedBy, Long projectId, ApplicationStatus statuses);
}
