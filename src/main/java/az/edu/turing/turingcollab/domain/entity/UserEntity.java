package az.edu.turing.turingcollab.domain.entity;

import az.edu.turing.turingcollab.model.enums.Field;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String email;

    private String university;

    @Enumerated(EnumType.STRING)
    private Field field;

    @Column(name = "profile_picture")
    private String profilePicture;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private List<ProjectEntity> projects = new ArrayList<>();

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private List<MentoriumEntity> mentoriums = new ArrayList<>();
}
