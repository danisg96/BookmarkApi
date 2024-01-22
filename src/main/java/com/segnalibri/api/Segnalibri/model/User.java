package com.segnalibri.api.Segnalibri.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usrs")
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Nonnull
    @Email
    @Column(name = "email")
    private String email;

    @Nonnull
    @Column(name = "created_at")
    private LocalDateTime createDate;

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Bookmark> bookmarks;

}
