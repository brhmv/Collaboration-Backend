package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.MentoriumApplicationEntity;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentoriumAppRepository extends JpaRepository<MentoriumApplicationEntity, Long> {

    List<MentoriumApplicationEntity> findAllByStatusIsAndCreatedBy(ApplicationStatus status, Long createdBy);

    List<MentoriumApplicationEntity> findAllByStatusIsAndMentorium_CreatedBy(ApplicationStatus status, Long createdBy);
}
