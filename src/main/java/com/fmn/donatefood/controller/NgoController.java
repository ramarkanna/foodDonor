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
import com.fmn.donatefood.domain.Ngo;
import com.fmn.donatefood.repository.NgoRepository;


/**
 * @author Kannadhasan
 *
 */
@RestController
@RequestMapping("/ngo")
public class NgoController {

    private static Logger logger = Logger.getLogger(NgoController.class.getName());

    @Autowired 
    private NgoRepository ngorepository;
    
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TastyDashResponse> createNgo(@RequestBody Ngo ngos) {
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		ngos.setCreateOn(new Date());
    		ngos =ngorepository.save(ngos);
    		response.setMessage(AprilFourMessage.RECORD_ADDED);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(ngos);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_ADD_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);
    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value="/{ngoId}")
    public ResponseEntity<TastyDashResponse> getCareerById(@PathVariable("ngoId") String ngoId){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		Ngo ngo = ngorepository.findOne(ngoId);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(ngo);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TastyDashResponse> updateCarrer(@RequestBody Ngo ngo ){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		ngo = ngorepository.save(ngo);
    		response.setMessage(AprilFourMessage.RECORD_UPDATED);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(ngo);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_UPDATE_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    
    @RequestMapping(method = RequestMethod.DELETE, value="/{ngoId}")
    public ResponseEntity<TastyDashResponse> deleteCareer(@PathVariable("ngoId") String ngoId){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		ngorepository.delete(ngoId);
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
    		List<Ngo> ngoList = ngorepository.findAll();
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(ngoList);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }

}