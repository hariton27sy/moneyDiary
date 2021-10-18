package com.example.moneydiary.controller;

import com.example.moneydiary.model.CreatedExpense;
import com.example.moneydiary.model.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class MoneyDiaryController {

    private final Random random = new SecureRandom();

    @PostMapping("/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Expenses<CreatedExpense> postExpenses(@RequestBody Expenses<Expense> expenses) {

        var createdExpenses = new ArrayList<CreatedExpense>();
        for (Expense expense : expenses.getExpenses()) {
            var createdExpense = new CreatedExpense(expense, random.nextLong());
            createdExpenses.add(createdExpense);
        }

        return new Expenses<>(createdExpenses);
    }

    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpense(@PathVariable long expenseId) {

    }

    @PatchMapping("/expenses/{expenseId}")
    public @ResponseBody
    CreatedExpense patchExpense(@PathVariable long expenseId, @RequestBody Expense expense) {
        return new CreatedExpense(expense, expenseId);
    }

    @GetMapping("/expenses")
    public @ResponseBody
    Expenses<CreatedExpense> getExpenses(
            @RequestParam Optional<Long> skip,
            @RequestParam Optional<Long> take,
            @RequestParam Optional<LocalDateTime> startTime,
            @RequestParam Optional<LocalDateTime> endTime) {
        return new Expenses<>(new ArrayList<>());
    }

    @GetMapping("/expenses/{expenseId}")
    public @ResponseBody
    CreatedExpense getExpense(@PathVariable long expenseId) {
        return new CreatedExpense();
    }

    @GetMapping("/expenses/summary")
    public @ResponseBody
    ExpensesSummary getExpensesSummary() {
        return new ExpensesSummary();
    }
}
