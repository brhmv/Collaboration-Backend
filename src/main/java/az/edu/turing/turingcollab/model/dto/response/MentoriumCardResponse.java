package az.edu.turing.turingcollab.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentoriumCardResponse {

    private Long id;
    private String shortDescription;
    private LocalDate applicationDeadline;
    private UserCardResponse creator;
    private String mentorField;
    private Integer participantCount;
    private Integer participantLimit;
    private Integer participantPercentage;
    private MultipartFile imageName;

    public Integer getParticipantPercentage() {
        if (participantLimit != null && participantLimit != 0 && participantCount != null) {
            return participantCount * 100 / participantLimit;
        }
        return 0;
    }
}
