package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.entity.MentoriumApplicationEntity;
import az.edu.turing.turingcollab.domain.entity.MentoriumEntity;
import az.edu.turing.turingcollab.domain.entity.UserEntity;
import az.edu.turing.turingcollab.domain.repository.MentoriumAppRepository;
import az.edu.turing.turingcollab.exception.AppNotFoundException;
import az.edu.turing.turingcollab.mapper.MentoriumAppMapper;
import az.edu.turing.turingcollab.model.dto.response.IncomingAppResponse;
import az.edu.turing.turingcollab.model.dto.response.SentAppResponse;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<IncomingAppResponse> getIncomingApps(Long userId) {
        userService.checkIfExists(userId);
        UserEntity userEntity = userService.findById(userId);
        return mentoriumAppRepository
                .findAllByStatusIsAndMentorium_CreatedBy(ApplicationStatus.PENDING, userId)
                .stream().map(a -> mentoriumAppMapper.toIncomingAppResponse(a, userEntity))
                .toList();
    }

    public List<SentAppResponse> getSentApps(Long userId) {
        userService.checkIfExists(userId);
        return mentoriumAppRepository.findAllByStatusIsAndCreatedBy(ApplicationStatus.PENDING, userId)
                .stream()
                .map(mentoriumAppMapper::toSentAppResponse)
                .toList();
    }

    public MentoriumApplicationEntity findById(Long id) {
        return mentoriumAppRepository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(id));
    }
}