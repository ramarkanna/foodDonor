package com.fmn.donatefood.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fmn.common.CommonUtils;
import com.fmn.donatefood.controller.FoodPickupController;
import com.fmn.donatefood.domain.FoodPickup;
import com.fmn.donatefood.domain.Ngo;
import com.fmn.donatefood.repository.NgoRepository;
import com.fmn.embedded.PostManTemplate;
import com.fmn.properties.DonorProperties;

@Service
@Transactional
public class FoodPickupServiceImpl implements FoodPickupService {

	@Autowired
	NgoRepository ngo;

	   private static Logger logger = Logger.getLogger(FoodPickupController.class.getName());

	   
	@Async
	@Override
	public void sendIssue2Mail(FoodPickup foodPickup) {

		List<Ngo> ngoList = ngo.findAll();

		StringBuffer mailids = new StringBuffer("");
		for (Ngo ng : ngoList) {
			if(ng.getEmail() != null ){
			mailids.append(ng.getEmail());
			mailids.append(',');
			}
		}


		String cusName = "";
		String emailId = mailids.toString();
		if (CommonUtils.validateString(emailId)) {

			DonorProperties tstdash = DonorProperties.getInstance();

			if (CommonUtils.validateString(emailId)) {
				
				String prefixurl = tstdash.getHosturl();

				PostManTemplate psttmpl = new PostManTemplate();
				psttmpl.setTemplateId(CommonUtils.NGO_PICKUP_TEMPL);
				psttmpl.setFrom(CommonUtils.POSTMAN_FROM_ADDR);
				psttmpl.setBcc(emailId);

				psttmpl.setInlineCss(true);
				Map tmplMap = psttmpl.getTemplateModel();
				tmplMap.put("comp_logo", tstdash.getCusomerAppBaseUrl() + "/" + "assets/images/logo-tastydash.png");
				tmplMap.put("product_name", "FreshMeals");

				tmplMap.put("ngo_name", cusName);
				tmplMap.put("cust_name", foodPickup.getName());
				tmplMap.put("cust_addr", foodPickup.getAddress());
				if (foodPickup.getCity() != null)
					tmplMap.put("cust_city", foodPickup.getCity());
				if (foodPickup.getPhone() != null)
					tmplMap.put("cust_phone", foodPickup.getPhone());
				if (foodPickup.getEmail() != null)
					tmplMap.put("cust_email", foodPickup.getEmail());

				if (foodPickup.getPickupDate() != null)
					tmplMap.put("pickup_Date", foodPickup.getPickupDate());
				tmplMap.put("pickup_Time", foodPickup.getPickupTime());
				tmplMap.put("sevings", foodPickup.getServings());
				tmplMap.put("whenfoddMade", foodPickup.getWhenFoodMade());
				
				String imgurl = foodPickup.getImageUrl();
				if(!imgurl.startsWith("http"))
					imgurl = prefixurl + "/" +imgurl;
				
				tmplMap.put("pickup_img",imgurl);
				if (foodPickup.getDescription() != null)
					tmplMap.put("pickup_desc", foodPickup.getDescription());

				String data = "";
				try {
					data = CommonUtils.getObjectMapperWithSerializationFeature(psttmpl);
				} catch (Exception ex) {
		    		logger.log(Level.ERROR,ex.getMessage(),ex);


				}
				CommonUtils.postmanMail(data);

			}

		}

	}

}
