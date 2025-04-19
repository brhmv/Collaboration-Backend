package az.edu.turing.turingcollab.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCardResponse {

    private Long id;
    private String imageName;
    private String fullName;
    private String field;
}
