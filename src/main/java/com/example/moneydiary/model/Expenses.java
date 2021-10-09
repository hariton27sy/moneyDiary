package com.example.moneydiary.model;

import java.util.List;

public class Expenses<T extends Expense> {

    private List<T> expenses;
    private long count;


    public Expenses() {
    }

    public Expenses(List<T> expenses) {
        setExpenses(expenses);
    }

    public List<T> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<T> expenses) {
        this.expenses = expenses;
        this.count = expenses.size();
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
