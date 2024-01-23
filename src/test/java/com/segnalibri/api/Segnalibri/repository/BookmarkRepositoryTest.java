package com.segnalibri.api.Segnalibri.repository;

import com.segnalibri.api.Segnalibri.builder.BookmarkBuilder;
import com.segnalibri.api.Segnalibri.builder.UserBuilder;
import com.segnalibri.api.Segnalibri.model.Bookmark;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookmarkRepositoryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Order(1)
    void shouldContainByUserIdTest() {
        userRepository.save(new UserBuilder()
                .setEmail("danisg96@hotmail.it")
                .createUser()
        );

        bookmarkRepository.save(new BookmarkBuilder()
                .setTitle("Sito1")
                .setDescription("Primo segnalibro")
                .setUrl("https://primosito.com")
                .setUserId(1)
                .createBookmark()
        );
        assertThat(bookmarkRepository.findAllByUserId(1)).hasSize(1);
    }

    @Test
    @Order(2)
    void shouldNotContainByUserIdTest() {
        assertThat(bookmarkRepository.findAllByUserId(2)).hasSize(0);
    }

}