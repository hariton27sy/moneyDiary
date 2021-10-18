package com.example.moneydiary.repository;

import com.example.moneydiary.dto.UserDto;
import com.example.moneydiary.dto.UserSessionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSessionDtoRepository extends JpaRepository<UserSessionDto, Long> {
    UserSessionDto findByRefreshTokenAndUser(UUID refreshToken, UserDto user);
}
