package com.example.moneydiary.repository;

import com.example.moneydiary.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
