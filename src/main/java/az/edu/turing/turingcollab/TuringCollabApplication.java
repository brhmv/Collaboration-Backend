package az.edu.turing.turingcollab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TuringCollabApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuringCollabApplication.class, args);
    }
}
