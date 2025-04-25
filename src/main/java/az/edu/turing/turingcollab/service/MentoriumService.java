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
import az.edu.turing.turingcollab.model.dto.response.PageResponse;
import az.edu.turing.turingcollab.model.enums.ErrorCode;
import az.edu.turing.turingcollab.model.enums.MentoriumStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public PageResponse<MentoriumCardResponse> getAllMentoriums(final int pageNumber, final int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var responses = mentoriumRepository.findAllByStatusIs(MentoriumStatus.ACCEPTED, pageable)
                .map(m -> mentoriumMapper.toCardResponse(m, userService.findById(m.getCreatedBy())));
        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    public MentoriumCardResponse getMentoriumById(Long id) {
        return mentoriumMapper.toCardResponse(mentoriumRepository.findById(id).orElseThrow(() ->
                new MentoriumNotFoundException(id)));
    }

    public PageResponse<MentoriumCardResponse> getAllByCreator(Long userId, final int pageNumber, final int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var responses = mentoriumRepository.findAllByCreatedBy(userId, pageable)
                .map(m -> mentoriumMapper.toCardResponse(m, userService.findById(m.getCreatedBy())));

        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    public PageResponse<MentoriumCardResponse> getAllByParticipant(Long userId, final int pageNumber, final int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var responses = mentoriumRepository.findAllByParticipants_Id(userId, pageable)
                .map(m -> mentoriumMapper.toCardResponse(m, userService.findById(m.getCreatedBy())));

        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    public PageResponse<MentoriumCardResponse> getAllSaved(Long userId, final int pageNumber, final int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var responses = mentoriumRepository.findAllBySavedByUsers_Id(userId, pageable)
                .map(m -> mentoriumMapper.toCardResponse(m, userService.findById(m.getCreatedBy())));

        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    public PageResponse<MentoriumCardResponse> getAllPending(Long userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var responses = mentoriumRepository
                .findAllByStatus(MentoriumStatus.PENDING, pageable)
                .map(m -> mentoriumMapper.toCardResponse(m, userService.findById(m.getCreatedBy())));

        return PageResponse.of(
                responses.getContent(),
                pageNumber,
                pageSize,
                responses.getTotalElements(),
                responses.getTotalPages());
    }

    @Transactional
    public Long update(Long userId, Long mentoriumId, MentoriumUpdateRequest request) {
        MentoriumEntity mentorium = findById(mentoriumId);

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

    @Transactional
    public void save(Long userId, Long mentoriumId) {
        if (!userService.findById(userId).getSavedMentoriums().add(findById(mentoriumId))) {
            throw new MentoriumAlreadyExistsException(mentoriumId);
        }
    }

    @Transactional
    public void deleteSaved(Long userId, Long mentoriumId) {
        if (!userService.findById(userId).getSavedMentoriums().remove(findById(mentoriumId))) {
            throw new MentoriumNotFoundException(mentoriumId);
        }
    }

    private void checkDateSequence(MentoriumCreateRequest request) {
        LocalDateTime startDate = LocalDateTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        LocalDateTime endDate = LocalDateTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        if (!(startDate.isBefore(endDate))) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public MentoriumEntity findById(Long mentoriumId) {
        return mentoriumRepository.findById(mentoriumId).orElseThrow(() -> new MentoriumNotFoundException(mentoriumId));
    }

    @Transactional
    public void accept(Long userId, Long mentoriumId) {
        //TODO: Check if user is admin
        findById(mentoriumId).setStatus(MentoriumStatus.ACCEPTED);
    }

    @Transactional
    public void reject(Long userId, Long mentoriumId) {
        //TODO: Check if user is admin
        findById(mentoriumId).setStatus(MentoriumStatus.REJECTED);
    }
}
