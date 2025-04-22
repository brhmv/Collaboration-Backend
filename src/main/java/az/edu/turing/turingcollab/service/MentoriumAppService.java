package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.entity.MentoriumApplicationEntity;
import az.edu.turing.turingcollab.domain.entity.MentoriumEntity;
import az.edu.turing.turingcollab.domain.repository.MentoriumAppRepository;
import az.edu.turing.turingcollab.exception.AppNotFoundException;
import az.edu.turing.turingcollab.mapper.MentoriumAppMapper;
import az.edu.turing.turingcollab.model.dto.response.IncomingAppResponse;
import az.edu.turing.turingcollab.model.dto.response.PageResponse;
import az.edu.turing.turingcollab.model.dto.response.SentAppResponse;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoriumAppService {

    private final MentoriumAppRepository mentoriumAppRepository;
    private final MentoriumAppMapper mentoriumAppMapper;
    private final MentoriumService mentoriumService;
    private final UserService userService;

    @Transactional
    public Long create(Long userId, Long mentoriumId) {
        userService.checkIfExists(userId);
        MentoriumEntity mentorium = mentoriumService.findById(mentoriumId);

        return mentoriumAppRepository
                .save(MentoriumApplicationEntity.builder()
                        .createdBy(userId)
                        .mentorium(mentorium)
                        .build())
                .getId();
    }

    @Transactional
    public void approve(Long mentoriumId) {
        MentoriumApplicationEntity applicationEntity = findById(mentoriumId);
        MentoriumEntity mentorium = mentoriumService.findById(applicationEntity.getMentorium().getId());

        applicationEntity.setStatus(ApplicationStatus.ACCEPTED);
        mentorium.addParticipant(userService.findById(applicationEntity.getCreatedBy()));
    }

    @Transactional
    public void reject(Long mentoriumId) {
        findById(mentoriumId).setStatus(ApplicationStatus.REJECTED);
    }

    public PageResponse<IncomingAppResponse> getIncomingApps(Long userId, final int pageNumber, final int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var responses = mentoriumAppRepository
                .findAllByStatusIsAndMentorium_CreatedBy(ApplicationStatus.PENDING, userId, pageable)
                .map(m -> mentoriumAppMapper.toIncomingAppResponse(m, userService.findById(m.getCreatedBy())));

        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    public PageResponse<SentAppResponse> getSentApps(Long userId, final int pageNumber, final int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var responses = mentoriumAppRepository.findAllByCreatedBy(userId, pageable)
                .map(mentoriumAppMapper::toSentAppResponse);

        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    public MentoriumApplicationEntity findById(Long id) {
        return mentoriumAppRepository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(id));
    }
}