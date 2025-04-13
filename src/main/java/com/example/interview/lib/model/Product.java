package com.example.interview.lib.model;

import lombok.Data;

@Data
public class Product {
	private long id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    public double getTotalPrice() {
        return price * quantity;
    }

}
