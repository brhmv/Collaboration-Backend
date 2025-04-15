package az.edu.turing.turingcollab.domain.entity;

import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Builder.Default
    private List<ProjectApplicationEntity> applications = new ArrayList<>();

    private String file;

    private String link;

    @Column(name = "image_name")
    private String imageName;

    @Builder.Default
    private ProjectStatus status = ProjectStatus.PENDING;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<UserEntity> participants = new HashSet<>();

    public void addParticipant(UserEntity user) {
        participants.add(user);
        user.getProjects().add(this);
    }

    public void removeParticipant(UserEntity user) {
        participants.remove(user);
        user.getProjects().remove(this);
    }
}
