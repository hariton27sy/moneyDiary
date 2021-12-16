package com.example.moneydiary.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.Hidden;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long expenseId;

    @Column
    protected String name;

    @Column
    protected Double amount;

    @Column(name = "date_time")
    protected LocalDateTime dateTime;

    @Column(name = "category_id")
    protected Long categoryId;

    @Column
    protected String description;

    public Expense() {

    }

    public Expense(Expense expense, Long expenseId) {
        setName(expense.getName());
        setAmount(expense.getAmount());
        setDateTime(expense.getDateTime());
        setCategoryId(expense.getCategoryId());
        setDescription(expense.getDescription());
        this.expenseId = expenseId;
    }

    @Override
    public String toString() {
        return String.format(
                "Expense[id=%d, name=%s, amount=%s, dateTime=%s, categoryId=%d, description=%s]",
                expenseId, name, amount, dateTime, categoryId, description
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getExpenseId() {
        return expenseId;
    }
}