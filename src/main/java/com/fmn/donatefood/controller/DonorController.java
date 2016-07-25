package com.fmn.donatefood.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aprilfour.common.AprilFourMessage;
import com.aprilfour.common.TastyDashResponse;

import com.fmn.common.CommonUtils;
import com.fmn.donatefood.domain.FoodDonor;
import com.fmn.donatefood.repository.FoodDonorRepository;

/**
 * @author Kannadhasan
 *
 */
@RestController
@RequestMapping("/donor")
public class DonorController {

    private static Logger logger = Logger.getLogger(DonorController.class.getName());

    @Autowired 
    private FoodDonorRepository donorrepository;
    
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TastyDashResponse> createDonor(@RequestBody FoodDonor donor) {
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		donor.setCreateOn(new Date());
    		donor =donorrepository.save(donor);
    		response.setMessage(AprilFourMessage.RECORD_ADDED);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(donor);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_ADD_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);
    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value="/{donorId}")
    public ResponseEntity<TastyDashResponse> getCareerById(@PathVariable("donorId") String donorId){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		FoodDonor donor = donorrepository.findOne(donorId);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(donor);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TastyDashResponse> updateCarrer(@RequestBody FoodDonor donor ){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		donor = donorrepository.save(donor);
    		response.setMessage(AprilFourMessage.RECORD_UPDATED);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(donor);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_UPDATE_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    
    @RequestMapping(method = RequestMethod.DELETE, value="/{donorId}")
    public ResponseEntity<TastyDashResponse> deleteCareer(@PathVariable("donorId") String donorId){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		donorrepository.delete(donorId);
    		response.setMessage(AprilFourMessage.RECORD_DELETED);
    		response.setStatus(AprilFourMessage.SUCCESS);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_DELETE_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping
    public ResponseEntity<TastyDashResponse> list() {
        TastyDashResponse response = new TastyDashResponse();
    	try{
    		List<FoodDonor> donorList = donorrepository.findAll();
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(donorList);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }

}