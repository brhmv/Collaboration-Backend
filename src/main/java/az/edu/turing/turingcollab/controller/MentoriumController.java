package az.edu.turing.turingcollab.controller;

import az.edu.turing.turingcollab.model.dto.request.MentoriumCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.MentoriumUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.MentoriumCardResponse;
import az.edu.turing.turingcollab.model.dto.response.PageResponse;
import az.edu.turing.turingcollab.service.MentoriumService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static az.edu.turing.turingcollab.model.constant.PageConstants.DEFAULT_PAGE_NUMBER;
import static az.edu.turing.turingcollab.model.constant.PageConstants.DEFAULT_PAGE_SIZE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentoriums")
public class MentoriumController {

    private final MentoriumService mentoriumService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> create(
            @RequestHeader("User-Id") Long userId,
            @Valid @ModelAttribute MentoriumCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentoriumService.create(userId, request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<MentoriumCardResponse>> getAll(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok(mentoriumService.getAllMentoriums(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentoriumCardResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mentoriumService.getMentoriumById(id));
    }

    @GetMapping("/creator")
    public ResponseEntity<PageResponse<MentoriumCardResponse>> getAllByCreator(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok().body(mentoriumService.getAllByCreator(userId, pageNumber, pageSize));
    }

    @GetMapping("/participant")
    public ResponseEntity<PageResponse<MentoriumCardResponse>> getAllByParticipant(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok().body(mentoriumService.getAllByParticipant(userId, pageNumber, pageSize));
    }

    @GetMapping("/saved")
    public ResponseEntity<PageResponse<MentoriumCardResponse>> getAllSaved(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok().body(mentoriumService.getAllSaved(userId, pageNumber, pageSize));
    }

    @GetMapping("/pending")
    public ResponseEntity<PageResponse<MentoriumCardResponse>> getAllPending(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok(mentoriumService.getAllPending(userId, pageNumber, pageSize));
    }

    @PutMapping("/{mentoriumId}")
    public ResponseEntity<Long> updateById(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long mentoriumId,
            @Valid @RequestBody MentoriumUpdateRequest request) {
        return ResponseEntity.ok(mentoriumService.update(userId, mentoriumId, request));
    }

    @PatchMapping("/save/{mentoriumId}")
    public ResponseEntity<Void> save(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long mentoriumId
    ) {
        mentoriumService.save(userId, mentoriumId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/delete-saved/{mentoriumId}")
    public ResponseEntity<Void> deleteSaved(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long mentoriumId
    ) {
        mentoriumService.deleteSaved(userId, mentoriumId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/accept/{mentoriumId}")
    public ResponseEntity<Void> accept(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long mentoriumId
    ) {
        mentoriumService.accept(userId, mentoriumId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reject/{mentoriumId}")
    public ResponseEntity<Void> reject(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long mentoriumId
    ) {
        mentoriumService.reject(userId, mentoriumId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{mentoriumId}")
    public ResponseEntity<Void> delete(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long mentoriumId) {
        mentoriumService.delete(mentoriumId, userId);
        return ResponseEntity.noContent().build();
    }
}

