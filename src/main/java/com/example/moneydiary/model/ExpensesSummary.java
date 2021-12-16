package com.example.moneydiary.model;

import java.util.Map;

public class ExpensesSummary {

    private Double totalAmount;

    private Map<Long, Double> totalAmountByCategoryId;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Map<Long, Double> getTotalAmountByCategoryId() {
        return totalAmountByCategoryId;
    }

    public void setTotalAmountByCategoryId(Map<Long, Double> totalAmountByCategoryId) {
        this.totalAmountByCategoryId = totalAmountByCategoryId;
    }

    public ExpensesSummary() {

    }

    public ExpensesSummary(Double totalAmount, Map<Long, Double> totalAmountByCategoryId) {
        setTotalAmount(totalAmount);
        setTotalAmountByCategoryId(totalAmountByCategoryId);
    }

    //TODO: add fields
}
