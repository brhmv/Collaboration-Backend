package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.ProjectEntity;
import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> getAllByStatusIsAndApplicationDeadlineAfter(ProjectStatus status, LocalDate deadline);

    List<ProjectEntity> getAllByCreatedBy(Long createdBy);
}
