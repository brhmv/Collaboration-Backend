package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.entity.MentoriumEntity;
import az.edu.turing.turingcollab.domain.repository.MentoriumRepository;
import az.edu.turing.turingcollab.exception.MentoriumAlreadyExistsException;
import az.edu.turing.turingcollab.exception.MentoriumNotFoundException;
import az.edu.turing.turingcollab.mapper.MentoriumMapper;
import az.edu.turing.turingcollab.model.dto.request.MentoriumCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.MentoriumUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.MentoriumCardResponse;
import az.edu.turing.turingcollab.model.enums.MentoriumStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoriumService {

    private final MentoriumRepository mentoriumRepository;
    private final MentoriumMapper mentoriumMapper;

    @Transactional
    public Long create(Long userId, MentoriumCreateRequest request) {
        if (mentoriumRepository.existsById(userId)) {
            throw new MentoriumAlreadyExistsException(userId);
        }

        MentoriumEntity mentorium = mentoriumMapper.toEntity(userId, request);
        MentoriumEntity savedMentorium = mentoriumRepository.save(mentorium);

        return savedMentorium.getId();
    }

    public List<MentoriumCardResponse> getAllMentoriums() {
        List<MentoriumEntity> mentoriums = mentoriumRepository.
                getAllByStatusIsAndApplicationDeadlineAfter(MentoriumStatus.ACCEPTED, LocalDate.now());

        return mentoriumMapper.toCardResponseList(mentoriums);
    }

    public MentoriumCardResponse getMentoriumById(Long id) {
        return mentoriumMapper.toCardResponse(mentoriumRepository.findById(id).orElseThrow(() ->
                new MentoriumNotFoundException(id)));
    }

    public List<MentoriumCardResponse> getByCreator(Long userId) {
        return mentoriumMapper.toCardResponseList(mentoriumRepository.findAllByCreatedBy(userId));
    }

    @Transactional
    public Long update(Long userId, Long mentoriumId, MentoriumUpdateRequest request) {
        MentoriumEntity mentorium = mentoriumRepository.findById(mentoriumId).orElseThrow(() ->
                new MentoriumNotFoundException(mentoriumId));

        mentoriumMapper.updateEntity(mentorium, userId, request);
        MentoriumEntity savedMentorium = mentoriumRepository.save(mentorium);

        return savedMentorium.getId();
    }

    @Transactional
    public void delete(Long mentoriumId, Long userId) {
        MentoriumEntity mentorium = mentoriumRepository.findById(mentoriumId).orElseThrow(() ->
                new MentoriumNotFoundException(mentoriumId));
        mentoriumRepository.delete(mentorium);
    }

    public List<MentoriumCardResponse> getSaved(Long userId) {
        List<MentoriumEntity> savedMentoriums = mentoriumRepository.findSavedMentoriumsByUserId(userId);

        return mentoriumMapper.toCardResponseList(savedMentoriums);
    }
}
