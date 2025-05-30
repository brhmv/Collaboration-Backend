package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.model.dto.request.MentoriumCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.MentoriumUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.MentoriumCardResponse;
import az.edu.turing.turingcollab.model.dto.response.MentoriumDetailedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoriumService {

    public Long create(Long userId, MentoriumCreateRequest request) {
        return null;
    }

    public List<MentoriumCardResponse> getAllMentoriums() {
        return null;
    }

    public MentoriumDetailedResponse getMentoriumById(Long mentoriumId) {
        return null;
    }

    public List<MentoriumCardResponse> getByCreator(Long userId) {
        return null;
    }

    public List<MentoriumCardResponse> getByParticipant(Long userId) {
        return null;
    }

    public Long update(Long userId, Long mentoriumId, MentoriumUpdateRequest request) {
        return null;
    }

    public void delete(Long mentoriumId, Long userId) {

    }
}
