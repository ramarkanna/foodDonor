package com.fmn.donatefood.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.QueryParam;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aprilfour.common.AprilFourMessage;
import com.aprilfour.common.TastyDashResponse;
import com.fmn.common.CommonUtils;
import com.fmn.donatefood.domain.FoodPickup;
import com.fmn.donatefood.repository.FoodPickupRepository;
import com.fmn.donatefood.service.FoodPickupService;
import com.fmn.properties.DonorProperties;
import com.fmn.donatefood.service.ImageService;

import com.fmn.embedded.Image;

/**
 * @author Kannadhasan
 *
 */
@RestController
@RequestMapping("/foodpickup")
public class FoodPickupController {

    private static Logger logger = Logger.getLogger(FoodPickupController.class.getName());

    @Autowired
	DonorProperties appProperties;
	
    @Autowired 
    private FoodPickupRepository pickuprepository;
    
    @Autowired 
    private ImageService imgService;
    
    @Autowired 
    private FoodPickupService foodService;
    
    private String PICKUP_UPLOAD_PATH="images/donor/pickup/";
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TastyDashResponse> createPickup(@RequestBody FoodPickup pickup) {
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		if(pickup.getImage() != null)
    		{
    			//add image to aws and get the path
    			Image img =pickup.getImage();
    			String imageUrlList = uploadRecipeImage(img);
    			if(imageUrlList != null)
    			{
    				pickup.setImageUrl(imageUrlList);
    			}
    			else{
    	    		response.setStatus(AprilFourMessage.FAILURE);
    	    		response.setMessage(AprilFourMessage.RECORD_ADD_ERROR);
    			}
    		}
    		pickup.setCreateOn(new Date());
    		DateTimeFormatter formatter = DateTimeFormat.forPattern("MM-dd-yyyy");
    		DateTime dt = formatter.parseDateTime(pickup.getPickupDate());
    				
    		pickup.setPickupDt(new Date (dt.getMillis()));
    		pickup.setPickupDone("N");
    		pickup =pickuprepository.save(pickup);
    		
    		// call to mail service 
    		foodService.sendIssue2Mail(pickup);

    		response.setMessage(AprilFourMessage.RECORD_ADDED);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(pickup);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_ADD_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);
    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value="/{pickupId}")
    public ResponseEntity<TastyDashResponse> getById(@PathVariable("pickupId") String pickupId){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		FoodPickup pickup = pickuprepository.findOne(pickupId);
       		String hostUrl = appProperties.getHosturl();
    			String iconUrl = pickup.getImageUrl();
    			if(CommonUtils.validateString(iconUrl)){
    				
    				iconUrl = hostUrl + iconUrl;
    			}
    			pickup.setImageUrl(iconUrl);

    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(pickup);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TastyDashResponse> update(@RequestBody FoodPickup pickup ){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		pickup = pickuprepository.save(pickup);
    		response.setMessage(AprilFourMessage.RECORD_UPDATED);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(pickup);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_UPDATE_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value="/update", method = RequestMethod.GET)
    public ResponseEntity<TastyDashResponse> updatePickup(@QueryParam("pickupId") String pickupId, @QueryParam("ngoId") String ngoId){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		FoodPickup pickup = pickuprepository.findByPickupId(pickupId);
    		if(pickup != null)
    		{
    		  if(pickup.getPickupDone() == null || pickup.getPickupDone().equals("N"))
    		  {
    			  pickup.setPickupDone("Y");
    			  pickup.setNgoId(ngoId);
    			 pickup =  pickuprepository.save(pickup);
    	    		response.setMessage(AprilFourMessage.RECORD_UPDATED);
    	    		response.setStatus(AprilFourMessage.SUCCESS);
    	    		response.setData(pickup);

    		  }else
    		  {
    	    		response.setMessage(AprilFourMessage.RECORD_UPDATE_ERROR);
    	    		response.setStatus(AprilFourMessage.ERROR);
    			  
    		  }
    	    }
    		else
    		{
	    		response.setMessage(AprilFourMessage.RECORD_UPDATE_ERROR);
	    		response.setStatus(AprilFourMessage.ERROR);
			  
    			
    		}
    		
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_UPDATE_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }

    
    @RequestMapping(method = RequestMethod.DELETE, value="/{pickupId}")
    public ResponseEntity<TastyDashResponse> delete(@PathVariable("pickupId") String pickupId){
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		pickuprepository.delete(pickupId);
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
    		List<FoodPickup> foodList = pickuprepository.findAll();
    		String hostUrl = appProperties.getHosturl();
    		for(FoodPickup fp : foodList)
    		{
    			String iconUrl = fp.getImageUrl();
    			if(CommonUtils.validateString(iconUrl)){
    				
    				iconUrl = hostUrl + iconUrl;
    			}
    			fp.setImageUrl(iconUrl);

    		}
    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(foodList);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/recent")
    public ResponseEntity<TastyDashResponse> recentPickup() {
        TastyDashResponse response = new TastyDashResponse();
    	try{
    		List<FoodPickup> foodList = pickuprepository.findByPickupDoneNotIn("Y");
    		
       		String hostUrl = appProperties.getHosturl();
    		for(FoodPickup fp : foodList)
    		{
    			String iconUrl = fp.getImageUrl();
    			if(CommonUtils.validateString(iconUrl)){
    				
    				iconUrl = hostUrl + iconUrl;
    			}
    			fp.setImageUrl(iconUrl);

    		}

    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(foodList);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }    
    @RequestMapping(value="/image", method = RequestMethod.POST)
    public ResponseEntity<TastyDashResponse> createHubProvider(@RequestBody Image image) {
    	TastyDashResponse response = new TastyDashResponse();
    	try{
    		String imageUrlList = uploadRecipeImage(image);
    		response.setMessage(AprilFourMessage.RECORD_ADDED);
    		response.setStatus(AprilFourMessage.SUCCESS);
    		Map<String, Object> data = new HashMap<String, Object>();
    		data.put("imageUrlList", imageUrlList);
    		response.setData(data);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.RECORD_ADD_ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }

    
    @RequestMapping(value="/confirmlist", method = RequestMethod.GET)
    public ResponseEntity<TastyDashResponse> listPickupByNgoId(@QueryParam("ngoId") String ngoId){
        TastyDashResponse response = new TastyDashResponse();
    	try{
    		List<FoodPickup> foodList = pickuprepository.findByNgoIdAndPickupDoneEquals(ngoId,"Y");
   
      		String hostUrl = appProperties.getHosturl();
    		for(FoodPickup fp : foodList)
    		{
    			String iconUrl = fp.getImageUrl();
    			if(CommonUtils.validateString(iconUrl)){
    				
    				iconUrl = hostUrl + iconUrl;
    			}
    			fp.setImageUrl(iconUrl);

    		}


    		response.setStatus(AprilFourMessage.SUCCESS);
    		response.setData(foodList);
    	}catch(Exception e){
    		response.setStatus(AprilFourMessage.FAILURE);
    		response.setMessage(AprilFourMessage.ERROR);
    		logger.log(Level.ERROR,e.getMessage(),e);

    	}
        return new ResponseEntity<TastyDashResponse>(response, HttpStatus.OK);
    }
    
	
	  private String uploadRecipeImage( Image image) {
			String imageUrlList =null ;
	    	try{
	    		 imageUrlList = imgService.uploadHubImageToS3FS(image, PICKUP_UPLOAD_PATH);
	    		
	    	}catch(Exception e){
	    		logger.log(Level.ERROR, e.getMessage(),e);	    	}
	        return imageUrlList;
	    }
	
}