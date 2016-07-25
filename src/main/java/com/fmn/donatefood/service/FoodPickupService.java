package com.fmn.donatefood.service;

import org.springframework.scheduling.annotation.Async;

import com.fmn.donatefood.domain.FoodPickup;

public interface FoodPickupService {

	@Async
	void sendIssue2Mail(FoodPickup pickupRequest);

	
	
	
}
