package com.example.moneydiary.repository;

import com.example.moneydiary.model.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    @Query(
            value = "SELECT * FROM expenses ORDER BY date_time OFFSET ?1 ROWS FETCH FIRST ?2 ROW ONLY;",
            nativeQuery = true
    )
    List<Expense> getExpenses(Long skip, Long take);
}
