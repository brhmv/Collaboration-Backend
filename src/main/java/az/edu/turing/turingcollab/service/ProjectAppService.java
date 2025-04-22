package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.entity.ProjectApplicationEntity;
import az.edu.turing.turingcollab.domain.entity.ProjectEntity;
import az.edu.turing.turingcollab.domain.repository.ProjectAppRepository;
import az.edu.turing.turingcollab.exception.AccessDeniedException;
import az.edu.turing.turingcollab.exception.AppNotFoundException;
import az.edu.turing.turingcollab.exception.BaseException;
import az.edu.turing.turingcollab.mapper.ProjectAppMapper;
import az.edu.turing.turingcollab.model.dto.response.IncomingAppResponse;
import az.edu.turing.turingcollab.model.dto.response.PageResponse;
import az.edu.turing.turingcollab.model.dto.response.SentAppResponse;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static az.edu.turing.turingcollab.model.enums.ErrorCode.BAD_REQUEST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectAppService {

    private final ProjectAppRepository projectAppRepository;
    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectAppMapper projectAppMapper;

    public PageResponse<SentAppResponse> getSent(Long userId, final int pageNumber, final int pageSize) {
        userService.checkIfExists(userId);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var responses = projectAppRepository.findAllByCreatedBy(userId, pageable)
                .map(projectAppMapper::toSentAppResponse);

        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    public PageResponse<IncomingAppResponse> getIncoming(Long userId, final int pageNumber, final int pageSize) {
        userService.checkIfExists(userId);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var responses = projectAppRepository
                .findAllByStatusIsAndProject_CreatedBy(ApplicationStatus.PENDING, userId, pageable)
                .map(a -> projectAppMapper.toIncomingAppResponse(a, userService.findById(a.getCreatedBy())));

        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    @Transactional
    public Long create(Long userId, Long projectId) {
        userService.checkIfExists(userId);
        ProjectEntity projectEntity = projectService.findById(projectId);
        if (!projectEntity.getStatus().equals(ProjectStatus.ACCEPTED) || projectEntity.getApplicationDeadline().isBefore(LocalDate.now())) {
            throw new BaseException("Can't create app to this project", BAD_REQUEST);
        }
        if (projectAppRepository.existsByCreatedByAndProject_IdAndStatus(
                userId,
                projectId,
                ApplicationStatus.PENDING)) {
            throw new BaseException("You already have pending application to this project", BAD_REQUEST);
        }
        if (projectAppRepository.existsByCreatedByAndProject_IdAndStatus(
                userId,
                projectId,
                ApplicationStatus.ACCEPTED)) {
            throw new BaseException("You are already participant to this project", BAD_REQUEST);
        }

        return projectAppRepository
                .save(ProjectApplicationEntity.builder()
                        .createdBy(userId)
                        .project(projectEntity)
                        .build())
                .getId();
    }

    @Transactional
    public void approve(Long userId, Long id) {
        var applicationEntity = findById(id);
        checkProjectCreator(applicationEntity, userId);
        ProjectEntity projectEntity = projectService.findById(applicationEntity.getProject().getId());

        applicationEntity.setStatus(ApplicationStatus.ACCEPTED);
        projectEntity.addParticipant(userService.findById(applicationEntity.getCreatedBy()));
    }

    @Transactional
    public void reject(Long userId, Long appId) {
        var applicationEntity = findById(appId);
        checkProjectCreator(applicationEntity, userId);
        findById(appId).setStatus(ApplicationStatus.REJECTED);
    }

    private void checkProjectCreator(ProjectApplicationEntity applicationEntity, Long userId) {
        if (!applicationEntity.getProject().getCreatedBy().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to this process");
        }
    }

    public ProjectApplicationEntity findById(Long id) {
        return projectAppRepository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(id));
    }
}
