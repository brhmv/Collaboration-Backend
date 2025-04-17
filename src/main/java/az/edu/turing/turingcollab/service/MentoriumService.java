package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.entity.MentoriumEntity;
import az.edu.turing.turingcollab.domain.repository.MentoriumRepository;
import az.edu.turing.turingcollab.exception.AccessDeniedException;
import az.edu.turing.turingcollab.exception.BaseException;
import az.edu.turing.turingcollab.exception.IllegalArgumentException;
import az.edu.turing.turingcollab.exception.MentoriumAlreadyExistsException;
import az.edu.turing.turingcollab.exception.MentoriumNotFoundException;
import az.edu.turing.turingcollab.mapper.MentoriumMapper;
import az.edu.turing.turingcollab.model.dto.request.MentoriumCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.MentoriumUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.MentoriumCardResponse;
import az.edu.turing.turingcollab.model.enums.ErrorCode;
import az.edu.turing.turingcollab.model.enums.MentoriumStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoriumService {

    private final MentoriumRepository mentoriumRepository;
    private final MentoriumMapper mentoriumMapper;
    private final FileStorageService fileStorageService;
    private final UserService userService;

    @Transactional
    public Long create(Long userId, MentoriumCreateRequest request) {
        if (mentoriumRepository.existsById(userId)) {
            throw new MentoriumAlreadyExistsException(userId);
        }

        checkDateSequence(request);

        MentoriumEntity mentorium = mentoriumMapper.toEntity(userId, request);

        try {
            mentorium.setImageName(fileStorageService.saveFile(request.getImage(), true));
        } catch (IOException e) {
            throw new BaseException("Something went wrong. Try again", ErrorCode.INTERNAL_ERROR);
        }

        MentoriumEntity savedMentorium = mentoriumRepository.save(mentorium);

        return savedMentorium.getId();
    }

    public List<MentoriumCardResponse> getAllMentoriums() {
        return mentoriumRepository.getAllByStatusIs(MentoriumStatus.ACCEPTED).stream().
                map(m -> mentoriumMapper.toCardResponse(m,
                        userService.findMentoriumById(m.getCreatedBy()))).toList();
    }

    public MentoriumCardResponse getMentoriumById(Long id) {
        return mentoriumMapper.toCardResponse(mentoriumRepository.findById(id).orElseThrow(() ->
                new MentoriumNotFoundException(id)));
    }

    public List<MentoriumCardResponse> getByCreator(Long userId) {
        return mentoriumRepository.findAllByCreatedBy(userId)
                .stream()
                .map(mentoriumMapper::toCardResponse)
                .toList();
    }

    @Transactional
    public Long update(Long userId, Long mentoriumId, MentoriumUpdateRequest request) {
        MentoriumEntity mentorium = mentoriumRepository.findById(mentoriumId).orElseThrow(() ->
                new MentoriumNotFoundException(mentoriumId));

        if (!mentorium.getCreatedBy().equals(userId)) {
            throw new AccessDeniedException("You do not have a permission to update this mentorium");
        }

        try {
            if (request.getImage() != null) {
                mentorium.setImageName(fileStorageService.updateFile(mentorium.getImageName(), request.getImage(), true));
            }
        } catch (IOException e) {
            throw new BaseException("Something went wrong. Try again", ErrorCode.INTERNAL_ERROR);
        }

        mentoriumMapper.updateEntity(mentorium, userId, request);
        MentoriumEntity savedMentorium = mentoriumRepository.save(mentorium);

        return savedMentorium.getId();
    }

    @Transactional
    public void delete(Long mentoriumId, Long userId) {
        MentoriumEntity mentorium = mentoriumRepository.findById(mentoriumId).orElseThrow(() ->
                new MentoriumNotFoundException(mentoriumId));

        if (!mentorium.getCreatedBy().equals(userId)) {
            throw new AccessDeniedException("You do not have a permission to delete this mentorium");
        }

        fileStorageService.deleteFile(mentorium.getImageName());

        mentoriumRepository.delete(mentorium);
    }

    private void checkDateSequence(MentoriumCreateRequest request) {
        LocalDateTime startDate = LocalDateTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        LocalDateTime endDate = LocalDateTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        if (!(startDate.isBefore(endDate))) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }
}
