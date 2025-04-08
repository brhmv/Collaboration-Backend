package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.model.dto.response.ApplicationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectAppService {
    public List<ApplicationResponse> getSent(Long userId) {
        return null;
    }

    public List<ApplicationResponse> getIncoming(Long userId) {
        return null;
    }

    public void approve(Long id) {

    }

    public void reject(Long id) {
    }

    public Long create(Long userId, Long projectId) {
        return null;
    }
}
