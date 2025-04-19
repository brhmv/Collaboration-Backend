package az.edu.turing.turingcollab.controller;

import az.edu.turing.turingcollab.model.dto.response.IncomingAppResponse;
import az.edu.turing.turingcollab.model.dto.response.SentAppResponse;
import az.edu.turing.turingcollab.service.MentoriumAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentorium-applications")
public class MentoriumAppController {

    private final MentoriumAppService mentoriumAppService;

    @PostMapping("/{mentoriumId}")
    public ResponseEntity<Long> create(@RequestHeader("User-Id") Long userId,
                                       @PathVariable Long mentoriumId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mentoriumAppService.create(userId, mentoriumId));
    }

    @GetMapping("/incoming")
    public ResponseEntity<List<IncomingAppResponse>> getIncomingApps(@RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok(mentoriumAppService.getIncomingApps(userId));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<SentAppResponse>> getSentApps(@RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok(mentoriumAppService.getSentApps(userId));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<Void> approve(@PathVariable Long id) {
        mentoriumAppService.approve(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<Void> reject(@PathVariable Long id) {
        mentoriumAppService.reject(id);
        return ResponseEntity.noContent().build();
    }
}
