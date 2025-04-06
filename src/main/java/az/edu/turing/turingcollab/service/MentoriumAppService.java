package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.model.dto.response.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoriumAppService {

    public Long create(Long userId, Long mentoriumId) {
        return null;
    }

    public void approve(Long mentoriumId) {

    }

    public void reject(Long mentoriumId) {

    }

    public List<ApplicationResponse> getIncomingApps(Long userId) {
        return null;
    }

    public List<ApplicationResponse> getSentApps(Long userId) {
        return null;
    }
}
