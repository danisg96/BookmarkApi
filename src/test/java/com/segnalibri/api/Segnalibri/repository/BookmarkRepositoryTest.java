package com.segnalibri.api.Segnalibri.repository;

import com.segnalibri.api.Segnalibri.builder.BookmarkBuilder;
import com.segnalibri.api.Segnalibri.builder.UserBuilder;
import com.segnalibri.api.Segnalibri.model.Bookmark;
import com.segnalibri.api.Segnalibri.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookmarkRepositoryTest {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void findAllByUserIdTest() {
        List<User> list = userRepository.findAll();
        assertThat(bookmarkRepository.findAllByUserId(1)).hasSize(1);
    }
}