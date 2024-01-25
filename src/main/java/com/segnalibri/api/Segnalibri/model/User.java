package com.segnalibri.api.Segnalibri.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Nonnull
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Bookmark> bookmarks;

    public User(){

    }

    public User(Integer id, @Nonnull String email, LocalDateTime createDate, LocalDateTime updateDate, List<Bookmark> bookmarks) {
        this.id = id;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.bookmarks = bookmarks;
    }
}
