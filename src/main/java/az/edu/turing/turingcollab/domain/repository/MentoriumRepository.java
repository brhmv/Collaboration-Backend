package az.edu.turing.turingcollab.domain.repository;

import az.edu.turing.turingcollab.domain.entity.MentoriumEntity;
import az.edu.turing.turingcollab.model.enums.MentoriumStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface MentoriumRepository extends JpaRepository<MentoriumEntity, Long> {

    List<MentoriumEntity> getAllByStatusIs(MentoriumStatus status);

    List<MentoriumEntity> findAllByCreatedBy(Long userId);

    @Query(value = """
                    SELECT m.* 
                    FROM mentorium m
                    JOIN user_saved_mentorium usm ON m.id = usm.mentorium_id
                    WHERE usm.user_id = :userId
            """, nativeQuery = true)
    List<MentoriumEntity> findSavedMentoriumsByUserId(@Param("userId") Long userId);

    void deleteAllByStatusAndUpdatedAtBetween(MentoriumStatus status, Instant start, Instant end);
}
