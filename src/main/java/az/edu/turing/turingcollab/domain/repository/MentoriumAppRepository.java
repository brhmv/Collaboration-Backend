package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.MentoriumApplicationEntity;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoriumAppRepository extends JpaRepository<MentoriumApplicationEntity, Long> {

    Page<MentoriumApplicationEntity> findAllByStatusIsAndCreatedBy(
            ApplicationStatus status, Long createdBy, Pageable pageable);

    Page<MentoriumApplicationEntity> findAllByStatusIsAndMentorium_CreatedBy(
            ApplicationStatus status, Long createdBy, Pageable pageable);

    Page<MentoriumApplicationEntity> findAllByCreatedBy(Long userId, Pageable pageable);
}
