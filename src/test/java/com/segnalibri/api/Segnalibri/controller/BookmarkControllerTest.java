package com.segnalibri.api.Segnalibri.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.segnalibri.api.Segnalibri.SegnalibriApplication;
import com.segnalibri.api.Segnalibri.config.JwtService;
import com.segnalibri.api.Segnalibri.config.SecurityConfig;
import com.segnalibri.api.Segnalibri.model.Bookmark;
import com.segnalibri.api.Segnalibri.repository.BookmarkRepository;
import com.segnalibri.api.Segnalibri.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = BookmarkController.class)
@ContextConfiguration(classes = {SegnalibriApplication.class, SecurityConfig.class, JwtService.class})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    BookmarkRepository bookmarkRepository;

    @Autowired
    ObjectMapper objectMapper;

    private Bookmark bookmark;

    @BeforeEach
    void setUp() {
        bookmark = Bookmark.builder()
                .title("Sito1")
                .description("Primo segnalibro")
                .url("https://primosito.com")
                .userId(1)
                .build();
    }

    @Test
    void userIsCreatedTest() throws Exception {
        given(bookmarkRepository.save(ArgumentMatchers.any(Bookmark.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/1/bookmark")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookmark)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldGetAllBookmark() throws Exception {
        when(bookmarkRepository.findAllByUserId(ArgumentMatchers.anyInt()))
                .thenReturn(List.of(bookmark));

        ResultActions response = mockMvc.perform(get("/api/1/bookmark")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", CoreMatchers.is("Sito1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url", CoreMatchers.is("https://primosito.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", CoreMatchers.is("Primo segnalibro")));
    }

    @Test
    void shouldFindById() throws Exception {
        given(bookmarkRepository.findById(ArgumentMatchers.anyInt()))
                .willAnswer(invocation -> Optional.of(Bookmark.builder()
                                .id(invocation.getArgument(0))
                                .title("Sito1")
                                .description("Primo segnalibro")
                                .url("https://primosito.com")
                                .userId(1)
                                .build())
                );
        ResultActions response = mockMvc.perform(get("/api/1/bookmark/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("Sito1")));
    }

    @Test
    void shouldNotFindById() throws Exception {
        when(bookmarkRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/api/1/bookmark/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldUpdateGivenId() throws Exception {
        given(bookmarkRepository.findById(ArgumentMatchers.anyInt()))
                .willAnswer(invocation -> Optional.of(Bookmark.builder()
                        .id(invocation.getArgument(0))
                        .title("Sito1")
                        .description("Primo segnalibro")
                        .url("https://primosito.com")
                        .userId(1)
                        .build())
                );

        given(bookmarkRepository.save(ArgumentMatchers.any(Bookmark.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/1/bookmark/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookmark)));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldNotFindIdAndNotPostTest() throws Exception {

        when(bookmarkRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(put("/api/1/bookmark/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookmark)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldDeleteById() throws Exception {
        doNothing().when(bookmarkRepository).deleteById(ArgumentMatchers.anyInt());

        ResultActions response = mockMvc.perform(delete("/api/1/bookmark/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldFindByTitle() throws Exception {
        Page<Bookmark> page = Mockito.mock(Page.class);
        when(page.getTotalElements()).thenReturn(1L);

        when(bookmarkRepository.findAllByUserIdAndTitleContaining(ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any())).thenReturn(page);

        ResultActions response = mockMvc.perform(get("/api/1/bookmark/title/i/0/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void ShouldNotFindByTitle() throws Exception{
        Page<Bookmark> page = Mockito.mock(Page.class);
        when(page.getTotalElements()).thenReturn(0L);

        when(bookmarkRepository.findAllByUserIdAndTitleContaining(ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any())).thenReturn(page);

        ResultActions response = mockMvc.perform(get("/api/1/bookmark/title/i/0/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}

