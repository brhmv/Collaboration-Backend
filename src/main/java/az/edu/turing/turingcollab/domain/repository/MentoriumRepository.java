package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.MentoriumEntity;
import az.edu.turing.turingcollab.model.enums.MentoriumStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface MentoriumRepository extends JpaRepository<MentoriumEntity, Long> {

    Page<MentoriumEntity> findAllByCreatedBy(Long userId, Pageable pageable);

    void deleteAllByStatusAndUpdatedAtBetween(MentoriumStatus status, Instant start, Instant end);

    Page<MentoriumEntity> findAllByStatusIs(MentoriumStatus mentoriumStatus, Pageable pageable);

    Page<MentoriumEntity> findAllByParticipants_Id(Long userId, Pageable pageable);

    Page<MentoriumEntity> findAllBySavedByUsers_Id(Long savedByUserId, Pageable pageable);

    Page<MentoriumEntity> findAllByStatus(MentoriumStatus projectStatus, Pageable pageable);
}
