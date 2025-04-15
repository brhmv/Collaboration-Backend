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

    private String shortDescription;
    private String applicationDeadline;
    private Long mentoriumId;
    private Long creator;
    private String mentorField;
    private Integer participantCount;
    private Integer participantLimit;
    private Integer participantPercentage = participantCount / participantLimit * 100;
    private MultipartFile imageName;
}
