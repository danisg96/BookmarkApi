package com.segnalibri.api.Segnalibri.controller;

import jakarta.validation.Valid;
import com.segnalibri.api.Segnalibri.model.Bookmark;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.segnalibri.api.Segnalibri.repository.BookmarkRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/{userId}/bookmark")
public class BookmarkController {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkController(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @GetMapping
    public List<Bookmark> findAll(@PathVariable Integer userId) {
        return bookmarkRepository.findAllByUserId(userId);
    }

    @GetMapping("/id")
    public Bookmark findById(@PathVariable Integer id) {
        return bookmarkRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, new StringBuilder()
                        .append("User with id ")
                        .append(id)
                        .append(" not found")
                        .toString()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Bookmark bookmark, @PathVariable Integer id) {
        bookmarkRepository.findById(id).ifPresentOrElse(
                u1 -> {
                    bookmark.setUpdateDate(LocalDateTime.now());
                    bookmarkRepository.save(bookmark);
                },
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, new StringBuilder()
                        .append("User with id ")
                        .append(id)
                        .append(" not found")
                        .toString())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Integer id) {
        bookmarkRepository.deleteById(id);
    }

    @GetMapping("/title/{title}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<Bookmark> findContainingTitle(@PathVariable String title, @PathVariable Integer userId) {
        return bookmarkRepository.findAllByUserId(userId)
                .stream().filter(bookmark -> bookmark.getTitle().toLowerCase()
                        .contains(title.toLowerCase()))
                .sorted((Bookmark b1, Bookmark b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()))
                .collect(Collectors.toList());
    }


}
