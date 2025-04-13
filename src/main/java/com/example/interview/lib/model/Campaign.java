package com.example.interview.lib.model;

import lombok.Data;

@Data
public class Campaign {
	private CampaignCategory category; 
	private DiscountType discountType; 
	private Parameter parameters;
}
