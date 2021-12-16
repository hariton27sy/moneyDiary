package com.example.moneydiary.dto;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;
    private String password;
    private String email;

    protected UserDto(){}

    public UserDto(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, username=%s, email=%s]", userId, username, email);
    }
}
