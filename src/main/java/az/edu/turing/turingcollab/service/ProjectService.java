package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.entity.ProjectEntity;
import az.edu.turing.turingcollab.domain.repository.ProjectRepository;
import az.edu.turing.turingcollab.exception.BaseException;
import az.edu.turing.turingcollab.exception.IllegalArgumentException;
import az.edu.turing.turingcollab.exception.InvalidDateFormatException;
import az.edu.turing.turingcollab.exception.ProjectAlreadyExistsException;
import az.edu.turing.turingcollab.exception.ProjectNotFoundException;
import az.edu.turing.turingcollab.mapper.ProjectMapper;
import az.edu.turing.turingcollab.model.dto.request.ProjectCreateRequest;
import az.edu.turing.turingcollab.model.dto.response.ProjectCardResponse;
import az.edu.turing.turingcollab.model.dto.response.ProjectDetailedResponse;
import az.edu.turing.turingcollab.model.enums.ErrorCode;
import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static az.edu.turing.turingcollab.model.constant.AppConstant.DATE_FORMAT_2;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final FileStorageService fileStorageService;

    public List<ProjectCardResponse> getAll() {
        return projectRepository
                .getAllByStatusIsAndApplicationDeadlineAfter(ProjectStatus.ACCEPTED, LocalDate.now())
                .stream().map(p -> projectMapper.toCardResponse(p, userService.findById(p.getCreatedBy())))
                .toList();
    }

    public ProjectDetailedResponse getById(Long id) {
        return projectMapper.toResponse(findById(id));
    }

    @Transactional
    public Long create(Long userId, ProjectCreateRequest request) {
        userService.checkIfExists(userId);
        validateRequest(request, false);

        ProjectEntity projectEntity = projectMapper.toEntity(userId, request);

        try {
            projectEntity.setImageName(fileStorageService.saveFile(request.getImage(), true));
            if (request.getFile() != null) {
                projectEntity.setFile(fileStorageService.saveFile(request.getFile(), false));
            }

        } catch (IOException e) {
            throw new BaseException("Something went wrong. Try again", ErrorCode.INTERNAL_ERROR);
        }

        return projectRepository.save(projectEntity).getId();
    }

    @Transactional
    public Long updateById(Long userId, Long projectId, ProjectCreateRequest request) {
        userService.checkIfExists(userId);
        validateRequest(request, true);

        ProjectEntity entity = findById(projectId);

        projectMapper.updateEntity(entity, userId, request);

        try {
            if (request.getImage() != null) {
                entity.setImageName(fileStorageService.updateFile(entity.getImageName(), request.getImage(), true));
            }
            if (request.getFile() != null) {
                entity.setFile(fileStorageService.updateFile(entity.getFile(), request.getFile(), false));
            }

        } catch (IOException e) {
            throw new BaseException("Something went wrong. Try again", ErrorCode.BAD_REQUEST);
        }

        return entity.getId();
    }

    public ProjectEntity findById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    public ProjectEntity findByIdAndAccepted(Long projectId) {
        return projectRepository.findByIdAndStatus(projectId, ProjectStatus.ACCEPTED)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    @Transactional
    public void delete(Long userId, Long projectId) {
        userService.checkIfExists(userId);
        ProjectEntity project = findById(projectId);
        fileStorageService.deleteFile(project.getImageName());
        fileStorageService.deleteFile(project.getFile());

        projectRepository.deleteById(projectId);
    }

    public List<ProjectCardResponse> getAllByCreatorId(Long userId) {
        userService.checkIfExists(userId);

        return projectRepository.getAllByCreatedBy(userId)
                .stream()
                .map(projectMapper::toCardResponse)
                .toList();
    }

    private void validateRequest(ProjectCreateRequest request, boolean isUpdate) {
        if ((request.getName() == null && !isUpdate) || (request.getName() != null && request.getName().isBlank())) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        if ((request.getDescription() == null && !isUpdate) || (request.getDescription() != null && request.getDescription().isBlank())) {
            throw new IllegalArgumentException("Description is mandatory");
        }
        if ((request.getShortDescription() == null && !isUpdate) || (request.getShortDescription() != null && request.getShortDescription().isBlank())) {
            throw new IllegalArgumentException("Short description is mandatory");
        }
        if ((request.getRequirements() == null && !isUpdate) || (request.getRequirements() != null && request.getRequirements().isBlank())) {
            throw new IllegalArgumentException("Requirements is mandatory");
        }
        if ((request.getStartDate() == null && !isUpdate) || (request.getStartDate() != null && request.getStartDate().isBlank())) {
            throw new IllegalArgumentException("Start date is mandatory");
        }
        if ((request.getEndDate() == null && !isUpdate) || (request.getEndDate() != null && request.getEndDate().isBlank())) {
            throw new IllegalArgumentException("End date is mandatory");
        }
        if ((request.getApplicationDeadline() == null && !isUpdate) || (request.getApplicationDeadline() != null && request.getApplicationDeadline().isBlank())) {
            throw new IllegalArgumentException("Application deadline is mandatory");
        }
        if ((request.getFields() == null && !isUpdate) || (request.getFields() != null && request.getFields().isBlank())) {
            throw new IllegalArgumentException("Fields is mandatory");
        }
        if (request.getImage().isEmpty()) {
            throw new IllegalArgumentException("Image is mandatory");
        }
        checkDateSequence(request);
    }

    private void checkDateSequence(ProjectCreateRequest request) {
        LocalDate startDate = validateDateFormat(request.getStartDate());
        LocalDate endDate = validateDateFormat(request.getEndDate());
        LocalDate applicationDeadline = validateDateFormat(request.getApplicationDeadline());

        if (!(applicationDeadline.isBefore(startDate) && startDate.isBefore(endDate))) {
            throw new IllegalArgumentException("Dates don't matching");
        }
    }

    private LocalDate validateDateFormat(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT_2));
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(date);
        }
    }

    public List<ProjectCardResponse> getAllByParticipant(Long userId) {
        return userService.findById(userId)
                .getProjects()
                .stream()
                .map(projectMapper::toCardResponse)
                .toList();
    }

    public List<ProjectCardResponse> getAllSaved(Long userId) {
        return userService.findById(userId)
                .getSavedProjects()
                .stream()
                .map(projectMapper::toCardResponse)
                .toList();
    }

    @Transactional
    public void save(Long userId, Long projectId) {
        if (!userService.findById(userId).getSavedProjects().add(findById(projectId))) {
            throw new ProjectAlreadyExistsException(projectId);
        }
    }

    @Transactional
    public void deleteSaved(Long userId, Long projectId) {
        if (!userService.findById(userId).getSavedProjects().remove(findById(projectId))) {
            throw new ProjectNotFoundException("Saved projects don't contain project with ID: " + projectId);
        }
    }
}
