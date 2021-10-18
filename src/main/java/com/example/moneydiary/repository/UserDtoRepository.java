package com.example.moneydiary.repository;

import com.example.moneydiary.dto.UserDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDtoRepository extends CrudRepository<UserDto, Long> {
}
