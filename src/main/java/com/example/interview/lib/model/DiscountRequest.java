package com.example.interview.lib.model;

import java.util.List;

import lombok.Data;

@Data
public class DiscountRequest {
	private List<Product> products;
	private List<Campaign> campaigns;
}
