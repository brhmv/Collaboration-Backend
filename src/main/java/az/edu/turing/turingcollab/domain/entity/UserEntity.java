package az.edu.turing.turingcollab.domain.entity;

import az.edu.turing.turingcollab.model.enums.Field;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"projects", "mentoriums", "savedProjects", "savedMentoriums"})
@Table(name = "users")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 12342942L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String email;

    private String university;

    @Enumerated(EnumType.STRING)
    private Field field;

    @Column(name = "image_name")
    private String imageName;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ProjectEntity> projects = new HashSet<>();

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MentoriumEntity> mentoriums = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_saved_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @Builder.Default
    private Set<ProjectEntity> savedProjects = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_saved_mentorium",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "mentorium_id"))
    @Builder.Default
    private Set<MentoriumEntity> savedMentoriums = new HashSet<>();
}
