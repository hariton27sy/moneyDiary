package com.example.moneydiary.repository;

import com.example.moneydiary.model.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    @Query(
            value = "SELECT * FROM expenses WHERE user_id = ?1 ORDER BY date_time OFFSET ?2 ROWS FETCH FIRST ?3 ROW ONLY;",
            nativeQuery = true
    )
    List<Expense> getExpenses(Long userId, Long skip, Long take);

    @Query(
            value = "SELECT * FROM expenses WHERE user_id = ?1 ",
            nativeQuery = true
    )
    List<Expense> getExpenses(Long userId);
}
