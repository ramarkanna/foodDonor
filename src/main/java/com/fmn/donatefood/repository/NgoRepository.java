package com.fmn.donatefood.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fmn.donatefood.domain.Ngo;



public interface NgoRepository extends MongoRepository<Ngo, String> {
	

	Ngo findByNgoId(String Id);
}
