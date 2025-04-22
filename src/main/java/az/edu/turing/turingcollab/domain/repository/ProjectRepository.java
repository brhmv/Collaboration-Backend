package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.ProjectEntity;
import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    Page<ProjectEntity> findAllByStatusIsAndApplicationDeadlineAfter(ProjectStatus status, LocalDate deadline, Pageable pageable);

    Page<ProjectEntity> findAllByCreatedBy(Long createdBy, Pageable pageable);

    Page<ProjectEntity> findAllByParticipants_Id(Long participantId, Pageable pageable);

    Page<ProjectEntity> findAllBySavedByUsers_Id(Long savedByUserId, Pageable pageable);

    void deleteAllByStatusAndUpdatedAtBetween(ProjectStatus status, Instant start, Instant end);

    Page<ProjectEntity> findAllByStatus(ProjectStatus status, Pageable pageable);
}
