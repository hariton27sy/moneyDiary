package com.example.moneydiary.model;

public class CreatedExpense extends Expense {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CreatedExpense() {

    }

    public CreatedExpense(Expense expense, long id) {
        setName(expense.getName());
        setAmount(expense.getAmount());
        setDateTime(expense.getDateTime());
        setCategoryId(expense.getCategoryId());
        setDescription(expense.getDescription());
        setId(id);
    }
}
