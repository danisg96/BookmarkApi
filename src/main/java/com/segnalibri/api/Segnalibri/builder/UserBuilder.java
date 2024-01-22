package com.segnalibri.api.Segnalibri.builder;

import com.segnalibri.api.Segnalibri.model.Bookmark;
import com.segnalibri.api.Segnalibri.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserBuilder {
    private Integer id;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<Bookmark> bookmarks;

    public UserBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public UserBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public UserBuilder setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        return this;
    }

    public User createUser() {
        return new User(id, email, Optional.ofNullable(createDate).orElse(LocalDateTime.now()), updateDate, bookmarks);
    }
}