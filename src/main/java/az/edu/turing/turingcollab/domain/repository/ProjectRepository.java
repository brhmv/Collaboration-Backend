package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.ProjectEntity;
import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> getAllByStatusIsAndApplicationDeadlineAfter(ProjectStatus status, LocalDate deadline);

    List<ProjectEntity> getAllByCreatedBy(Long createdBy);

    void deleteAllByStatusAndUpdatedAtBetween(ProjectStatus status, Instant start, Instant end);

    Optional<ProjectEntity> findByIdAndStatus(Long id, ProjectStatus status);

    List<ProjectEntity> findAllByStatus(ProjectStatus status);
}
