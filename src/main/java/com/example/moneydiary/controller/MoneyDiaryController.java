package com.example.moneydiary.controller;

import com.example.moneydiary.dto.UserDto;
import com.example.moneydiary.model.*;
import com.example.moneydiary.repository.ExpenseRepository;
import com.example.moneydiary.repository.UserDtoRepository;
import com.example.moneydiary.service.PasswordEncryptor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api")
public class MoneyDiaryController {

    private ExpenseRepository expenseRepository;
    private UserDtoRepository userDtoRepository;
    private PasswordEncryptor passwordEncryptor;
    private ObjectFactory<RequestContext> requestContextObjectFactory;


    @Autowired
    public MoneyDiaryController(
            ExpenseRepository expenseRepository,
            UserDtoRepository userDtoRepository,
            PasswordEncryptor passwordEncryptor,
            ObjectFactory<RequestContext> requestContextObjectFactory) {

        this.expenseRepository = expenseRepository;
        this.userDtoRepository = userDtoRepository;
        this.passwordEncryptor = passwordEncryptor;
        this.requestContextObjectFactory = requestContextObjectFactory;
    }

    private UserDto getUser() {
        var context = requestContextObjectFactory.getObject();
        var userSession = context.getUser();
        return userDtoRepository.findById(userSession.getUserId()).get();
    }

    @PostMapping("/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Expenses postExpenses(@RequestBody List<Expense> expenses) {

        var user = getUser();
        expenses.forEach(expense -> expense.setUserId(user.getUserId()));

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
    Expenses getExpenses(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam Optional<LocalDateTime> from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam Optional<LocalDateTime> to,
            @RequestParam Optional<Long> categoryId,
            @RequestParam Optional<String> keyword
    ) {
        var user = getUser();

        var result = getExpensesByFilters(user, from, to, categoryId, keyword);

        return new Expenses(result);
    }

    @GetMapping("/expenses/{expenseId}")
    public @ResponseBody
    ResponseEntity<Expense> getExpense(@PathVariable Long expenseId) {
        return expenseRepository.findById(expenseId)
                .map(expense -> new ResponseEntity<>(expense, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    private List<Expense> getExpensesByFilters(
            UserDto user,
            Optional<LocalDateTime> from,
            Optional<LocalDateTime> to,
            Optional<Long> categoryId,
            Optional<String> keyword
    ) {
        var expenses = expenseRepository.getExpenses(user.getUserId());

        return expenses
                .stream()
                .filter(expense -> from.filter(x -> !expense.getDateTime().isAfter(x)).isEmpty())
                .filter(expense -> to.filter(x -> !expense.getDateTime().isBefore(x)).isEmpty())
                .filter(expense -> categoryId.filter(x -> !Objects.equals(expense.getCategoryId(), x)).isEmpty())
                .filter(expense -> keyword.filter(x -> !(expense.getName().contains(x) || expense.getDescription().contains(x))).isEmpty())
                .toList();
    }

    @GetMapping("/expenses/summary")
    public @ResponseBody
    ExpensesSummary getExpensesSummary(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam Optional<LocalDateTime> from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam Optional<LocalDateTime> to,
            @RequestParam Optional<Long> categoryId,
            @RequestParam Optional<String> keyword
    ) {

        var user = getUser();
        var result = getExpensesByFilters(user, from, to, categoryId, keyword);
        var totalAmount = 0.0;
        var totalAmountByCategoryId = new HashMap<Long, Double>();

        for (Expense expense : result) {
            totalAmount += expense.getAmount();

            var updatedTotalAmountByCategory  =
                    totalAmountByCategoryId.getOrDefault(expense.getCategoryId(), 0.0) + expense.getAmount();
            totalAmountByCategoryId.put(expense.getCategoryId(), updatedTotalAmountByCategory);
        }

        return new ExpensesSummary(totalAmount, totalAmountByCategoryId);
    }
}
