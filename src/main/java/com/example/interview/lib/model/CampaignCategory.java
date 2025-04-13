package com.example.interview.lib.model;

import com.example.interview.lib.exception.ValidationException;

public enum CampaignCategory {
	COUPON("Coupon"), ON_TOP("On Top"), SEASONAL("Seasonal");

	private final String value;

	CampaignCategory(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
    public static CampaignCategory lookup(String param) {
        for (CampaignCategory type : CampaignCategory.values()) {
            if (type.getValue().equalsIgnoreCase(param.trim())) {
                return type;
            }
        }
       return null;
    }

}
