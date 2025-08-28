package com.findnbook.findnbook_backend.service;

import com.findnbook.findnbook_backend.model.User;
import com.findnbook.findnbook_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {

        if (user.getRole() == null) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }
}