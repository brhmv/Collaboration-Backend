package az.edu.turing.turingcollab.domain.entity;

import az.edu.turing.turingcollab.model.enums.Field;
import az.edu.turing.turingcollab.model.enums.LessonType;
import az.edu.turing.turingcollab.model.enums.MentoriumStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "mentorium")
public class MentoriumEntity extends BaseEntity {

    @Column(name = "mentor_fullname", nullable = false)
    private String mentorFullName;

    @Column(name = "mentor_field", nullable = false)
    @Enumerated(EnumType.STRING)
    private Field mentorField;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(nullable = false)
    private String topic;

    @Enumerated(EnumType.STRING)
    private MentoriumStatus status = MentoriumStatus.PENDING;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    private String place;

    @Column(name = "lesson_type", nullable = false)
    private LessonType lessonType;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    private String picture;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "mentorium_users",
            joinColumns = @JoinColumn(name = "mentorium_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<UserEntity> participants = new HashSet<>();

    public void addParticipant(UserEntity user) {
        participants.add(user);
        user.getMentoriums().add(this);
    }

    public void removeParticipant(UserEntity user) {
        participants.remove(user);
        user.getMentoriums().remove(this);
    }
}
