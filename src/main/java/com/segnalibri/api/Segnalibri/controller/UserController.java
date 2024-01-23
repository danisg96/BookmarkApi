package com.segnalibri.api.Segnalibri.controller;

import com.segnalibri.api.Segnalibri.exception.EmailFoundException;
import com.segnalibri.api.Segnalibri.exception.UserNotFoundException;
import com.segnalibri.api.Segnalibri.model.User;
import com.segnalibri.api.Segnalibri.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,new StringBuilder()
                        .append("User with id ")
                        .append(id)
                        .append(" not found")
                        .toString()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresentOrElse(u1 -> new EmailFoundException(new StringBuilder()
                                .append("Email ")
                                .append(u1.getEmail())
                                .append(" already in use")
                                .toString()),
                        () -> userRepository.save(user)
                );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable Integer id) {
        userRepository.findById(id).ifPresentOrElse(
                u1 -> {
                    user.setUpdateDate(LocalDateTime.now());
                    userRepository.save(user);
                },
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,new StringBuilder()
                        .append("User with id ")
                        .append(id)
                        .append(" not found")
                        .toString())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

}
