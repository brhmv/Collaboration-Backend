package az.edu.turing.turingcollab.domain.entity;

import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "project")
public class ProjectEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    private String requirements;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "application_deadline", nullable = false)
    private LocalDate applicationDeadline;

    private String fields;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectApplicationEntity> applications = new ArrayList<>();

    @Column(name = "additional_link")
    private String additionalLink;

    private String picture;

    private ProjectStatus status;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private List<UserEntity> participants = new ArrayList<>();

    public void addParticipant(UserEntity user) {
        participants.add(user);
        user.getProjects().add(this);
    }

    public void removeParticipant(UserEntity user) {
        participants.remove(user);
        user.getProjects().remove(this);
    }
}
