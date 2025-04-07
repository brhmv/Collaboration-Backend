package az.edu.turing.turingcollab.controller;

import az.edu.turing.turingcollab.model.dto.request.MentoriumCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.MentoriumUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.MentoriumCardResponse;
import az.edu.turing.turingcollab.model.dto.response.MentoriumDetailedResponse;
import az.edu.turing.turingcollab.service.MentoriumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentoriums")
public class MentoriumController {

    private final MentoriumService mentoriumService;

    @PostMapping
    public ResponseEntity<Long> create(
            @RequestHeader("User-Id") Long userId,
            @Valid @RequestBody MentoriumCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentoriumService.create(userId, request));
    }

    @PostMapping("/images/{id}")
    public ResponseEntity<Void> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file

    ) {
        mentoriumService.uploadImage(id, file);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MentoriumCardResponse>> getAll() {
        return ResponseEntity.ok(mentoriumService.getAllMentoriums());
    }

    @GetMapping("/saved")
    public ResponseEntity<List<MentoriumCardResponse>> getSaved(@RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok(mentoriumService.getSaved());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentoriumDetailedResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mentoriumService.getMentoriumById(id));
    }

    @GetMapping("/creator")
    public ResponseEntity<List<MentoriumCardResponse>> getByCreator(@RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok().body(mentoriumService.getByCreator(userId));
    }

    @GetMapping("/participant")
    public ResponseEntity<List<MentoriumCardResponse>> getByParticipant(@RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok().body(mentoriumService.getByParticipant(userId));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> getImage(
            @PathVariable Long id) {
        return ResponseEntity.ok(mentoriumService.getImage(id));
    }

    @PutMapping("/{mentoriumId}")
    public ResponseEntity<Long> updateById(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long mentoriumId,
            @Valid @RequestBody MentoriumUpdateRequest request) {
        return ResponseEntity.ok(mentoriumService.update(userId, mentoriumId, request));
    }

    @DeleteMapping("/{mentoriumId}")
    public ResponseEntity<Void> delete(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long mentoriumId) {
        mentoriumService.delete(mentoriumId, userId);
        return ResponseEntity.noContent().build();
    }
}

