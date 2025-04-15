package az.edu.turing.turingcollab.service;

import az.edu.turing.turingcollab.domain.entity.UserEntity;
import az.edu.turing.turingcollab.domain.repository.UserRepository;
import az.edu.turing.turingcollab.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findById(Long createdBy) {
        return null;
    }

    public void checkUserExists(Long id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException(id);
    }
}
