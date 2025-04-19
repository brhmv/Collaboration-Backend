package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.repository.MentoriumRepository;
import az.edu.turing.turingcollab.domain.repository.ProjectAppRepository;
import az.edu.turing.turingcollab.domain.repository.ProjectRepository;
import az.edu.turing.turingcollab.model.enums.ApplicationStatus;
import az.edu.turing.turingcollab.model.enums.MentoriumStatus;
import az.edu.turing.turingcollab.model.enums.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class CleanUpService {

    private final ProjectAppRepository projectAppRepository;
    private final ProjectRepository projectRepository;
    private final MentoriumRepository mentoriumRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteRejectedApplications() {
        Instant now = Instant.now();
        Instant start = now.minus(3, ChronoUnit.DAYS);
        Instant end = start.plus(1, ChronoUnit.DAYS);

        projectAppRepository.deleteAllByStatusAndUpdatedAtBetween(ApplicationStatus.REJECTED, start, end);
        projectRepository.deleteAllByStatusAndUpdatedAtBetween(ProjectStatus.REJECTED, start, end);
        mentoriumRepository.deleteAllByStatusAndUpdatedAtBetween(MentoriumStatus.REJECTED, start, end);
    }
}
