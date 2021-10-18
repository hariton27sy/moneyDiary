package com.example.moneydiary.dto;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user_sessions")
public class UserSessionDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private UUID refreshToken;
    private Date expiresIn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDto user;

    protected UserSessionDto() {
    }

    public UserSessionDto(UUID refreshToken, Date expiresIn, UserDto user) {
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public UUID getRefreshToken() {
        return refreshToken;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public UserDto getUser() {
        return user;
    }
}
