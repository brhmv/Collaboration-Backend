package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.repository.ProjectAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static az.edu.turing.turingcollab.model.enums.ApplicationStatus.REJECTED;

@Service
@Transactional
@RequiredArgsConstructor
public class AppCleanUpService {

    private final ProjectAppRepository projectAppRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteRejectedApplications() {
        Instant now = Instant.now();
        Instant start = now.minus(3, ChronoUnit.DAYS);
        Instant end = start.plus(1, ChronoUnit.DAYS);

        projectAppRepository.deleteAllByStatusAndUpdatedAtBetween(REJECTED, start, end);
    }
}
