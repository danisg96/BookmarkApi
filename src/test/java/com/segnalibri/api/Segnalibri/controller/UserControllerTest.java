package com.segnalibri.api.Segnalibri.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.segnalibri.api.Segnalibri.config.JwtService;
import com.segnalibri.api.Segnalibri.dto.UserDto;
import com.segnalibri.api.Segnalibri.mapper.UserMapper;
import com.segnalibri.api.Segnalibri.model.User;
import com.segnalibri.api.Segnalibri.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static com.segnalibri.api.Segnalibri.model.Role.ADMIN;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    JwtService jwtService;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserMapper userMapper;

    private User user;

    private final

    @BeforeEach
    void setUp() {
        user = User
                .builder()
                .email("danisg96@hotmail.it")
                .password("password")
                .role(ADMIN)
                .build();
    }

    @Test
    void userIsCreatedTest() throws Exception {
        given(userRepository.save(ArgumentMatchers.any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void userEmailAlreadyFoundTest() throws Exception {
        when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(user));

        ResultActions response = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void shouldGetAllUser() throws Exception {
        when(userRepository.findAll()).thenReturn(List.of(User.builder().email("mockito@gmail.com").build()));

        given(userMapper.EntityToDto(ArgumentMatchers.any(User.class)))
                .willAnswer( invocation -> UserDto
                        .builder()
                        .email("mockito@gmail.com")
                        .build()
                );

        ResultActions response = mockMvc.perform(get("/api/user")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", CoreMatchers.is("mockito@gmail.com")));
    }

    @Test
    void shouldFindById() throws Exception {
        given(userRepository.findById(ArgumentMatchers.anyInt()))
                .willAnswer(invocation -> Optional.of(User.builder()
                        .id(invocation.getArgument(0))
                        .email("danisg96@hotmail.it")
                        .role(ADMIN)
                        .build())
                );

        given(userMapper.EntityToDto(ArgumentMatchers.any(User.class)))
                .willAnswer( invocation -> UserDto
                        .builder()
                        .email("danisg96@hotmail.it")
                        .build()
                );

        ResultActions response = mockMvc.perform(get("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is("danisg96@hotmail.it")));
    }

    @Test
    void shouldNotFindById() throws Exception {
        when(userRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldUpdateGivenId() throws Exception {
        when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        given(userRepository.findById(ArgumentMatchers.anyInt()))
                .willAnswer(invocation -> Optional.of(User.builder()
                        .id(invocation.getArgument(0))
                        .email("danisg96@hotmail.it")
                        .role(ADMIN)
                        .build())
                );

        given(userMapper.DtoToEntity(ArgumentMatchers.any(UserDto.class)))
                .willAnswer( invocation -> User
                        .builder()
                        .email("danisg96@hotmail.it")
                        .role(ADMIN)
                        .build()
                );

        given(userRepository.save(ArgumentMatchers.any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserDto
                        .builder()
                        .email(user.getUsername())
                        .build())));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldFindEmailAndNotPostTest() throws Exception {
        when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(User.builder()
                        .id(1)
                        .email("danisg96@hotmail.it")
                        .role(ADMIN)
                        .build()));

        ResultActions response = mockMvc.perform(put("/api/user/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UserDto
                        .builder()
                        .email(user.getUsername())
                        .build())));

        response.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void shouldNotFindIdAndNotPostTest() throws Exception {
        when(userRepository.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        when(userRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldDeleteById() throws Exception {
        doNothing().when(userRepository).deleteById(ArgumentMatchers.anyInt());

        ResultActions response = mockMvc.perform(delete("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
