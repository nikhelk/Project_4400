package com.example.brand_new_4400;


public class Wf8_tuple {
    private String productName;
    private String amount;
    Wf8_tuple(String productName, String amount) {
        this.productName = productName;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }
    public String getAmount() {
        return amount;
    }
}