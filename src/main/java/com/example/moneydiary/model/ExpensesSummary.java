package com.example.moneydiary.model;

public class ExpensesSummary {

    private Double totalAmount;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ExpensesSummary() {

    }

    public ExpensesSummary(Double totalAmount) {
        setTotalAmount(totalAmount);
    }

    //TODO: add fields
}
