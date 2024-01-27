package com.segnalibri.api.Segnalibri.controller;

import com.segnalibri.api.Segnalibri.dto.UserDto;
import com.segnalibri.api.Segnalibri.exception.EmailFoundException;
import com.segnalibri.api.Segnalibri.exception.UserNotFoundException;
import com.segnalibri.api.Segnalibri.mapper.UserMapper;
import com.segnalibri.api.Segnalibri.model.User;
import com.segnalibri.api.Segnalibri.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream().map(userMapper::EntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, new StringBuilder()
                        .append("User with id ")
                        .append(id)
                        .append(" not found")
                        .toString()));
        return userMapper.EntityToDto(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserDto userDto) throws ResponseStatusException {
        userRepository.findByEmail(userDto.email())
                .ifPresentOrElse(u1 -> {
                            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, new StringBuilder()
                                    .append("Email  ")
                                    .append(u1.getEmail())
                                    .append(" already in use")
                                    .toString());
                        },
                        () -> userRepository.save(userMapper.DtoToEntity(userDto))
                );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
        userRepository.findByEmail(userDto.email())
                .ifPresent(u1 -> {
                    if (u1.getId() != id)
                        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, new StringBuilder()
                                .append("Email ")
                                .append(u1.getEmail())
                                .append(" already in use")
                                .toString());
                });
        userRepository.findById(id).ifPresentOrElse(
                u1 -> {
                    User user = userMapper.DtoToEntity(userDto);
                    user.setUpdateDate(LocalDateTime.now());
                    userRepository.save(user);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, new StringBuilder()
                            .append("User with id ")
                            .append(id)
                            .append(" not found")
                            .toString());
                }
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

}
