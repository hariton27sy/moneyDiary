package com.example.moneydiary.controller;

import com.example.moneydiary.model.*;
import com.example.moneydiary.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MoneyDiaryController {

    private ExpenseRepository expenseRepository;

    @Autowired
    public MoneyDiaryController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @PostMapping("/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Expenses postExpenses(@RequestBody List<Expense> expenses) {
        return new Expenses(expenseRepository.saveAll(expenses));
    }

    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpense(@PathVariable Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @PatchMapping("/expenses/{expenseId}")
    public @ResponseBody
    Expense patchExpense(@PathVariable Long expenseId, @RequestBody Expense expense) {
        return expenseRepository.save(new Expense(expense, expenseId));
    }

    @GetMapping("/expenses")
    public @ResponseBody
    Expenses getExpenses(@RequestParam Long skip, @RequestParam Long take) {
        return new Expenses(expenseRepository.getExpenses(skip, take));
    }

    @GetMapping("/expenses/{expenseId}")
    public @ResponseBody
    ResponseEntity<Expense> getExpense(@PathVariable Long expenseId) {
        return expenseRepository.findById(expenseId)
                .map(expense -> new ResponseEntity<>(expense, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/expenses/summary")
    public @ResponseBody
    ExpensesSummary getExpensesSummary(@RequestParam Long skip, @RequestParam Long take) {

        var expenses = expenseRepository.getExpenses(skip, take);
        var totalAmount = 0.0;

        for (Expense expense : expenses) {
            totalAmount += expense.getAmount();
        }

        return new ExpensesSummary(totalAmount);
    }
}
