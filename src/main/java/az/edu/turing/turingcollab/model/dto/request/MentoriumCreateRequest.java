package az.edu.turing.turingcollab.model.dto.request;

import az.edu.turing.turingcollab.model.enums.LessonType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentoriumCreateRequest {

    @NotNull
    private String topic;

    private String mentorField;

    private String mentorFullName;

    @NotNull
    private String shortDescription;

    private Integer participantLimit;

    private String startTime;

    private String endTime;

    private String place;

    @NotNull
    private LessonType lessonType;

    private MultipartFile image;
}
