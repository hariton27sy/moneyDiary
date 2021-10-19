package com.example.moneydiary.model;

public class Expenses {

    private Iterable<Expense> expenses;

    public Expenses() {
    }

    public Expenses(Iterable<Expense> expenses) {
        setExpenses(expenses);
    }

    public Iterable<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Iterable<Expense> expenses) {
        this.expenses = expenses;
    }
}
