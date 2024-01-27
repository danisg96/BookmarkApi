package com.segnalibri.api.Segnalibri.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Nonnull
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateDate;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(Integer id, @Nonnull String email, String password, LocalDateTime createDate, LocalDateTime updateDate, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
