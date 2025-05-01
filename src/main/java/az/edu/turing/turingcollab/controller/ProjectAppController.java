package az.edu.turing.turingcollab.controller;

import az.edu.turing.turingcollab.model.dto.response.IncomingAppResponse;
import az.edu.turing.turingcollab.model.dto.response.PageResponse;
import az.edu.turing.turingcollab.model.dto.response.SentAppResponse;
import az.edu.turing.turingcollab.service.ProjectAppService;
import jakarta.validation.constraints.Min;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static az.edu.turing.turingcollab.model.constant.PageConstants.DEFAULT_PAGE_NUMBER;
import static az.edu.turing.turingcollab.model.constant.PageConstants.DEFAULT_PAGE_SIZE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/project-applications")
public class ProjectAppController {

    private final ProjectAppService projectAppService;

    @GetMapping("/incoming")
    public ResponseEntity<PageResponse<IncomingAppResponse>> getIncomingApps(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok(projectAppService.getIncoming(userId, pageNumber, pageSize));
    }

    @GetMapping("/sent")
    public ResponseEntity<PageResponse<SentAppResponse>> getSent(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok(projectAppService.getSent(userId, pageNumber, pageSize));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<Void> approve(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long id) {
        projectAppService.approve(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reject/{id}")
    public ResponseEntity<Void> reject(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long id) {
        projectAppService.reject(userId, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<Long> create(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long projectId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectAppService.create(userId, projectId));
    }
}
