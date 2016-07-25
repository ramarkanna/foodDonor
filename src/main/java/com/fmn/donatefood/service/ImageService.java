package com.fmn.donatefood.service;

import java.util.List;

import com.fmn.embedded.Image;


public interface ImageService {

	public List<String> uploadHubImagesToS3FS(List<Image> images, String imagePath);
	
	public String uploadHubImageToS3FS(Image image, String imagePath);
}
