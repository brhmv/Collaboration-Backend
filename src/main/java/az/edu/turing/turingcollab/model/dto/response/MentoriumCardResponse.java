package az.edu.turing.turingcollab.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentoriumCardResponse {

    private Long id;
    private String topic;
    private UserCardResponse creator;
    private String mentorField;
    private Integer participantCount;
    private Integer participantLimit;
    private Integer participantPercentage;
    private String imageName;

    public Integer getParticipantPercentage() {
        if (participantLimit != null && participantLimit != 0 && participantCount != null) {
            return participantCount * 100 / participantLimit;
        }
        return 0;
    }
}
