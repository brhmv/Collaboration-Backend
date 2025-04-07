package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.model.dto.request.ProjectCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.ProjectUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.ProjectCardResponse;
import az.edu.turing.turingcollab.model.dto.response.ProjectDetailedResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProjectService {

    public List<ProjectCardResponse> getAll() {
        return null;
    }

    public ProjectDetailedResponse getById(Long id) {
        return null;
    }

    public Long create(Long userId, ProjectCreateRequest request) {
        return null;
    }

    public Long updateById(Long userId, Long projectId, ProjectUpdateRequest request) {
        return null;
    }

    public void delete(Long userId, Long projectId) {
    }

    public List<ProjectCardResponse> getByCreatorId(Long userId) {
        return null;
    }

    public List<ProjectCardResponse> getByParticipant(Long userId) {
        return null;
    }

    public List<ProjectCardResponse> getSaved(Long userId) {
        return null;
    }

    public Resource getImage(Long id) {
        return null;
    }

    public void uploadImage(Long id, MultipartFile file) {
    }
}
