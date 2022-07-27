package com.example.brand_new_4400;

public class Product_amount_tuple {
    private String name;
    private Integer amount;
    Product_amount_tuple(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }
    public Integer getAmount() {
        return amount;
    }
}