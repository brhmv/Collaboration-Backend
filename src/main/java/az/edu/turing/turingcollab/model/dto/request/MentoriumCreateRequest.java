package az.edu.turing.turingcollab.model.dto.request;

import az.edu.turing.turingcollab.model.enums.LessonType;
import jakarta.validation.constraints.Future;
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

    @NotNull
    private String shortDescription;

    private Integer participantLimit;

    @NotNull
    @Future
    private String startDate;

    @NotNull
    @Future
    private String endDate;

    private String place;

    @NotNull
    private LessonType lessonType;

    private MultipartFile imageName;
}
