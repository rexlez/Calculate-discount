package com.example.interview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.interview.lib.model.Campaign;
import com.example.interview.lib.model.CampaignCategory;
import com.example.interview.lib.model.Product;
import com.example.interview.utill.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalculateDiscountService {

	
	public double calculateDiscount(List<Product> productsCard, List<Campaign> campaigns) {
		double currentPrice = productsCard.stream().mapToDouble(Product::getTotalPrice).sum();
		List<CampaignCategory> orderApply  =   List.of(CampaignCategory.COUPON, CampaignCategory.ON_TOP, CampaignCategory.SEASONAL);

		// Step 1: Group campaigns by category
		Map<CampaignCategory, Campaign> selectedCampaigns = new HashMap<>();
		for (Campaign campaign : campaigns) {
			selectedCampaigns.putIfAbsent(campaign.getCategory(), campaign); // Apply only 1 per category
		}
	
		
		// Step 2:  in order: COUPON -> ON_TOP -> SEASONAL
		for (CampaignCategory category : orderApply) {
			Campaign campaign = selectedCampaigns.get(category);
			if (campaign != null) {
				currentPrice = Utils.applySingleDiscount(currentPrice, productsCard, campaign);
			}
		}

		return currentPrice;
	}
	
}
