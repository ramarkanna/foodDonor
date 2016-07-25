package com.fmn.donatefood.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fmn.donatefood.domain.FoodPickup;




public interface FoodPickupRepository extends MongoRepository<FoodPickup, String> {
	

	FoodPickup findByPickupId(String Id);
	List<FoodPickup> findByPickupDoneNotIn(String done);
	List<FoodPickup> findByNgoIdAndPickupDoneEquals(String ngoId,String pickudone);
	
}
