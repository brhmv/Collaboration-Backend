package az.edu.turing.turingcollab.controller;

import az.edu.turing.turingcollab.model.dto.request.ProjectCreateRequest;
import az.edu.turing.turingcollab.model.dto.request.ProjectUpdateRequest;
import az.edu.turing.turingcollab.model.dto.response.ProjectCardResponse;
import az.edu.turing.turingcollab.model.dto.response.ProjectDetailedResponse;
import az.edu.turing.turingcollab.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectCardResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectDetailedResponse>> getById(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getById(projectId));
    }

    @GetMapping("/creator")
    public ResponseEntity<List<ProjectCardResponse>> getByCreator(
            @RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok().body(projectService.getByCreatorId(userId));
    }

    @GetMapping("/participant")
    public ResponseEntity<List<ProjectCardResponse>> getByParticipant(
            @RequestHeader("User-Id") Long userId) {
        return ResponseEntity.ok().body(projectService.getByParticipant(userId));
    }

    @PostMapping
    public ResponseEntity<Long> create(
            @RequestHeader("User-Id") Long userId,
            @RequestBody @Valid ProjectCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(userId, request));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Long> updateById(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long projectId,
            @RequestBody @Valid ProjectUpdateRequest request
    ) {
        return ResponseEntity.ok(projectService.updateById(userId, projectId, request));
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
