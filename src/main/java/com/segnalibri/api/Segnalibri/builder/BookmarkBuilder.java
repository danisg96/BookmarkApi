package com.segnalibri.api.Segnalibri.builder;

import com.segnalibri.api.Segnalibri.model.Bookmark;

import java.time.LocalDateTime;
import java.util.Optional;

public class BookmarkBuilder {
    private Integer id;
    private String title;
    private String description;
    private String url;
    private int userId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public BookmarkBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public BookmarkBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookmarkBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public BookmarkBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public BookmarkBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public BookmarkBuilder setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public BookmarkBuilder setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Bookmark createBookmark() {
        return new Bookmark(id, title, description, url, userId, Optional.ofNullable(createDate).orElse(LocalDateTime.now()), updateDate);
    }
}