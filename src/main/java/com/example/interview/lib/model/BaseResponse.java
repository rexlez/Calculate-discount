package com.example.interview.lib.model;

import lombok.Data;

@Data
public class BaseResponse<T> {
	private String status;
	private String masage;
	private T result;
	
	public BaseResponse() {
		
	}
	public BaseResponse(T result) {
		this.result = result;
	}
}
