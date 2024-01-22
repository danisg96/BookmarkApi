package com.segnalibri.api.Segnalibri.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmark")
@Data
@AllArgsConstructor
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Nonnull
    @Column(name = "created_at")
    private LocalDateTime createDate;

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

}
