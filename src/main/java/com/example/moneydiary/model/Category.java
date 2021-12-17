package com.example.moneydiary.model;

import javax.persistence.*;

@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true)
    private String categoryName;

    public Category() {

    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
