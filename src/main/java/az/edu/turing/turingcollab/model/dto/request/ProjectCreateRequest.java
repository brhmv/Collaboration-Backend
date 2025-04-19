package az.edu.turing.turingcollab.model.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateRequest {

    private String name;
    private String description;
    private String shortDescription;
    private String requirements;
    private String startDate;
    private String endDate;
    private String applicationDeadline;
    private String fields;
    private String link;
    private MultipartFile file;
    private MultipartFile image;
}
