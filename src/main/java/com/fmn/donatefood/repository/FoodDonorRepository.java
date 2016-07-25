package com.fmn.donatefood.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fmn.donatefood.domain.FoodDonor;




public interface FoodDonorRepository extends MongoRepository<FoodDonor, String> {
	

	FoodDonor findByDonorId(String Id);
}
