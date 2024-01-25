package com.segnalibri.api.Segnalibri.repository;

import com.segnalibri.api.Segnalibri.model.Bookmark;
import com.segnalibri.api.Segnalibri.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookmarkRepositoryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    UserRepository userRepository;

    private User user;

    private Bookmark bookmark;

    @BeforeEach
    void setUp() {
        user = User
                .builder()
                .email("danisg96@hotmail.it")
                .build();

        bookmark = Bookmark
                .builder()
                .title("Sito1")
                .description("Primo segnalibro")
                .url("https://primosito.com")
                .userId(1)
                .build();
    }

    @Test
    @Order(1)
    void shouldContainByUserIdTest() {
        userRepository.save(user);
        bookmark.setUserId(userRepository
                .findAll()
                .get(0)
                .getId());
        bookmarkRepository.save(bookmark);

        assertThat(bookmarkRepository.findAllByUserId(bookmark.getUserId())).hasSize(1);
    }

    @Test
    @Order(2)
    void shouldNotContainByUserIdTest() {
        assertThat(bookmarkRepository.findAllByUserId(99)).hasSize(0);
    }

    @Test
    @Order(3)
    void shouldContainTitleLikeWithPaging() {
        userRepository.save(user);
        bookmark.setUserId(userRepository
                .findAll()
                .get(0)
                .getId());
        bookmarkRepository.save(bookmark);

        Pageable pr = PageRequest.of(0, 10);

        assertThat(bookmarkRepository.findAllByUserIdAndTitleContaining(bookmark.getUserId(), "Si", pr)).hasSize(1);
    }

    @Test
    @Order(4)
    void shouldExactlyContainTitleLikeWithPagingInDifferentPages() {
        userRepository.save(user);
        bookmark.setUserId(userRepository
                .findAll()
                .get(0)
                .getId());
        bookmarkRepository.save(bookmark);

        bookmarkRepository.save(Bookmark
                .builder()
                .title("Sito2")
                .description("Secondo segnalibro")
                .url("https://secondosito.com")
                .userId(bookmark.getUserId())
                .build()
        );

        Pageable pr = PageRequest.of(0, 1);

        assertThat(bookmarkRepository.findAllByUserIdAndTitleContaining(bookmark.getUserId(), "Si", pr)).hasSize(1);

        Pageable pr2 = PageRequest.of(1, 1);
        assertThat(bookmarkRepository.findAllByUserIdAndTitleContaining(1, "Si", pr2)).hasSize(1);
    }

    @Test
    @Order(5)
    void shouldNotContainTitleLikeWithPagingOutOfPage() {
        userRepository.save(user);
        bookmark.setUserId(userRepository
                .findAll()
                .get(0)
                .getId());
        bookmarkRepository.save(bookmark);

        Pageable pr = PageRequest.of(1, 1);

        assertThat(bookmarkRepository.findAllByUserIdAndTitleContaining(1, "Si", pr)).hasSize(0);
    }

}