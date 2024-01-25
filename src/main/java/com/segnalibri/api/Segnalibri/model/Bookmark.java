package com.segnalibri.api.Segnalibri.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmark")
@Getter
@Setter
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Nonnull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Nonnull
    @Column(name = "url")
    private String url;

    @Column(name = "user_id")
    private int userId;

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    public Bookmark(){

    }

    public Bookmark(Integer id, @Nonnull String title, String description, @Nonnull String url, int userId, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
