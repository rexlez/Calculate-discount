package com.example.interview.utill;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.example.interview.lib.exception.ValidationException;
import com.example.interview.lib.model.Campaign;
import com.example.interview.lib.model.Product;

public class Utils {
	
	public static double applySingleDiscount(double currentPrice, List<Product> product, Campaign campaign) {
		
		switch (campaign.getDiscountType()) {
		case FIXED_AMOUNT:
			double amount =  validIsEmpty(campaign.getParameters().getAmount());
			return Math.max(0, currentPrice - amount);
		
		case PERCENTAGE:
			double percentage = validIsEmpty(campaign.getParameters().getPercentage());
			return currentPrice * (1 - (percentage / 100));
	
		case PERCENTAGE_BY_CATEGORY:
			String category = validIsEmpty(campaign.getParameters().getCategory());
			double percent =  validIsEmpty(campaign.getParameters().getPercentage());
			double discountable = product.stream().filter(p -> p.getCategory().equalsIgnoreCase(category))
					.mapToDouble(Product::getTotalPrice).sum();
			return currentPrice - (discountable * (percent / 100));
		
		case POINTS:
			int points = validIsEmpty(campaign.getParameters().getCustomerPoint());
			double maxAllowed = currentPrice * 0.2; //20% of currentPrice
			return currentPrice - Math.min(points, maxAllowed);
	
		case SPECIAL:
			double everyX = validIsEmpty(campaign.getParameters().getEveryX());
			double discountY = validIsEmpty(campaign.getParameters().getDiscountY());
			int times = (int) (currentPrice / everyX);
			return currentPrice - (times * discountY);
		
		default:
			throw new ValidationException("Unsupported discount type: " + campaign.getDiscountType());
		}
	}
	

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        
        if (obj instanceof Integer) {
            return obj == null ;
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        return false;

    }
    
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);

    }
    
    
    public static <T> T validIsEmpty(T obj) {
        if (isEmpty(obj)) {
            throw new ValidationException("Validation failed: Value of type " +
                (obj != null ? obj.getClass().getSimpleName() : "null") + " cannot be null or empty");
        }
        return obj;
    }
    
    
}
