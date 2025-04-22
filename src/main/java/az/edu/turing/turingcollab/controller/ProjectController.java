package az.edu.turing.turingcollab.controller;

import az.edu.turing.turingcollab.model.dto.request.ProjectCreateRequest;
import az.edu.turing.turingcollab.model.dto.response.PageResponse;
import az.edu.turing.turingcollab.model.dto.response.ProjectCardResponse;
import az.edu.turing.turingcollab.model.dto.response.ProjectDetailedResponse;
import az.edu.turing.turingcollab.service.ProjectService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static az.edu.turing.turingcollab.model.constant.PageConstants.DEFAULT_PAGE_NUMBER;
import static az.edu.turing.turingcollab.model.constant.PageConstants.DEFAULT_PAGE_SIZE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<PageResponse<ProjectCardResponse>> getAll(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok(projectService.getAll(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailedResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    @GetMapping("/creator")
    public ResponseEntity<PageResponse<ProjectCardResponse>> getAllByCreator(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize) {
        return ResponseEntity.ok().body(projectService.getAllByCreatorId(userId, pageNumber, pageSize));
    }

    @GetMapping("/participant")
    public ResponseEntity<PageResponse<ProjectCardResponse>> getAllByParticipant(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize) {
        return ResponseEntity.ok().body(projectService.getAllByParticipant(userId, pageNumber, pageSize));
    }

    @GetMapping("/saved")
    public ResponseEntity<PageResponse<ProjectCardResponse>> getAllSaved(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize) {
        return ResponseEntity.ok().body(projectService.getAllSaved(userId, pageNumber, pageSize));
    }

    @GetMapping("/pending")
    public ResponseEntity<PageResponse<ProjectCardResponse>> getAllPending(
            @RequestHeader("User-Id") Long userId,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) @Min(0) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) @Min(1) int pageSize
    ) {
        return ResponseEntity.ok(projectService.getAllPending(userId, pageNumber, pageSize));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> create(
            @RequestHeader("User-Id") Long userId,
            @ModelAttribute ProjectCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(userId, request));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Long> updateById(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long projectId,
            @ModelAttribute ProjectCreateRequest request
    ) {
        return ResponseEntity.ok(projectService.updateById(userId, projectId, request));
    }

    @PatchMapping("/save/{projectId}")
    public ResponseEntity<Void> save(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.save(userId, projectId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/delete-saved/{projectId}")
    public ResponseEntity<Void> deleteSaved(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.deleteSaved(userId, projectId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/accept/{projectId}")
    public ResponseEntity<Void> accept(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.accept(userId, projectId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reject/{projectId}")
    public ResponseEntity<Void> reject(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.reject(userId, projectId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> delete(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.delete(userId, projectId);
        return ResponseEntity.noContent().build();
    }
}
