package com.fmn.donatefood.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.util.json.JSONObject;
import com.fmn.common.CommonUtils;
import com.fmn.common.util.AmazonS3FSCRUD;

import com.fmn.embedded.Image;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {


	@Override
	public String uploadHubImageToS3FS(Image image, String imagePath) {
		String imageUrl = "";
		if(image != null){
			String imageString = image.getImageBase64();
			String imageType = image.getImageType();
			//String imagePath = "images/hub/";
			if(CommonUtils.validateString(imageString) && CommonUtils.validateString(imageType)){
				String imageName = UUID.randomUUID().toString().replace("-", "");
				try {
					JSONObject json = AmazonS3FSCRUD.uploadImageToS3(imageString, imageType, imagePath, imageName);
					if(json.has("isUploaded") && json.getBoolean("isUploaded")){
						imageUrl = "resources/"+imagePath+imageName+"."+imageType;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return imageUrl;
	}
	
	@Override
	public List<String> uploadHubImagesToS3FS(List<Image> images, String imagePath) {
		List<String> imageUrls = new ArrayList<String>();
		if(images!=null){
			for(Image image:images){
				String imageUrl = uploadHubImageToS3FS(image, imagePath);
				if(CommonUtils.validateString(imageUrl))
					imageUrls.add(imageUrl);
			}
		}
		return imageUrls;
	}
	
	public String getImageUrl(String imagePath){
		String imageUrl = "";
		try {
			imageUrl = AmazonS3FSCRUD.getImageUrlFromS3(imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageUrl;
	}
	
	public List<String> getImageUrls(List<String> photoLocations){
		List<String> imageUrls = new ArrayList<String>();
		for(String imagePath:photoLocations){
			String imageUrl = getImageUrl(imagePath);
			if(imageUrl!=null && !imageUrl.equals(""))
				imageUrls.add(imageUrl);
		}
		return imageUrls;
	}

}
