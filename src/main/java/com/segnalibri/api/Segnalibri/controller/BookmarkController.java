package com.segnalibri.api.Segnalibri.controller;

import com.segnalibri.api.Segnalibri.model.Bookmark;
import com.segnalibri.api.Segnalibri.repository.BookmarkRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/{id}")
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
    public void delete(Integer id) {
        bookmarkRepository.deleteById(id);
    }

    @GetMapping("/title/{title}/{pageNro}/{pageSize}")
    public List<Bookmark> getTitlePaging(@PathVariable int userId,
                                         @PathVariable String title,
                                         @PathVariable int pageNro,
                                         @PathVariable int pageSize){
        Pageable pr = PageRequest.of(pageNro,pageSize);
        Page<Bookmark> result = bookmarkRepository.findAllByUserIdAndTitleContaining(userId,
                title,
                pr);

        if (result.getTotalElements() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No bookmarks found");

        return result.getContent();
    }
}
