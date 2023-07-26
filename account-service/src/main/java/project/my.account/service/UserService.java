package project.my.account.service;

import org.springframework.stereotype.Service;
import project.my.account.entity.User;
import project.my.account.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return user.getId();
    }
}
