package com.example.interview.contraller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.interview.lib.exception.ValidationException;
import com.example.interview.lib.model.BaseResponse;
import com.example.interview.lib.model.Campaign;
import com.example.interview.lib.model.DiscountRequest;
import com.example.interview.lib.model.DiscountResponse;
import com.example.interview.lib.model.Product;
import com.example.interview.service.CalculateDiscountService;
import com.example.interview.utill.Utils;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/discount")
@Slf4j
public class CalculateDiscountController {

	private final CalculateDiscountService calculateDiscountService;

	public CalculateDiscountController(CalculateDiscountService calculateDiscountService) {
		this.calculateDiscountService = calculateDiscountService;
	}

	@PostMapping("/apply-discount")
	public ResponseEntity<BaseResponse<DiscountResponse>> applyDiscount(@RequestBody DiscountRequest requestBody) {
		log.debug("Entryring applyDiscount");
		BaseResponse<DiscountResponse> responseDTO = new BaseResponse<DiscountResponse>();
		DiscountResponse discountResponse = new DiscountResponse();
		try {

			if (Utils.isEmpty(discountResponse)) {
				throw new ValidationException("requestBody cannot be null");
			}
			if (Utils.isEmpty(requestBody.getProducts())) {
				throw new ValidationException("Products cannot be null or empty");
			}
			if (Utils.isEmpty(requestBody.getCampaigns())) {
				throw new ValidationException("Campaigns cannot be null or empty");
			}

			List<Product> products = requestBody.getProducts();
			List<Campaign> campaigns = requestBody.getCampaigns();

			double finalPrice = calculateDiscountService.calculateDiscount(products, campaigns);
			discountResponse.setTotalPrice(finalPrice);
			responseDTO.setResult(discountResponse);
			responseDTO.setMasage("SUCCESS");
			responseDTO.setStatus(HttpStatus.OK.name());

		} catch (ValidationException e) {
			responseDTO.setMasage(e.getMessage());
			responseDTO.setStatus(HttpStatus.BAD_REQUEST.toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);

		} catch (Exception e) {
			responseDTO.setMasage("INTERNAL_SERVER_ERROR");
			responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
		}
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}

}
