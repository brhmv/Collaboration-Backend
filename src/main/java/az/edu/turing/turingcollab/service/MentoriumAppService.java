package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.model.dto.response.IncomingAppResponse;
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

    public List<IncomingAppResponse> getIncomingApps(Long userId) {
        return null;
    }

    public List<IncomingAppResponse> getSentApps(Long userId) {
        return null;
    }
}
