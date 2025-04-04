package az.edu.turing.turingcollab.domain.entity;

import az.edu.turing.turingcollab.model.enums.LessonType;
import az.edu.turing.turingcollab.model.enums.MentoriumStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentoriumEntity extends BaseEntity {

    private String mentorFullName;
    private String mentorField;
    private String shortDescription;
    private String topic;
    private MentoriumStatus status;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<UserEntity> participants;
    private String place;
    private LessonType lessonType;
    private Integer participantLimit;
    private String picture;
}
