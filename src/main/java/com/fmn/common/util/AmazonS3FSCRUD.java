package com.fmn.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.json.JSONObject;
import com.fmn.properties.DonorProperties;

public final class AmazonS3FSCRUD {
	final static Logger log = Logger.getLogger(AmazonS3FSCRUD.class.getName());
	
	static DonorProperties props = DonorProperties.getInstance();

	private static AmazonS3 awsConnection = null;

	static String AWS_SECRET_KEY;
	static String AWS_ACCESS_KEY;
	static String BUCKET_NAME;
	static String S3FS_DIR_PATH;

	public AmazonS3FSCRUD() {
		/*Properties properties = new Properties();
		try {
			properties.load(CommonUtil.class.getResourceAsStream(
					"/properties/serverurl.properties"));
			AWS_SECRET_KEY = properties.getProperty("AWS_S3FS_SECRET_KEY");
			AWS_ACCESS_KEY = properties.getProperty("AWS_S3FS_ACCESS_KEY");
			BUCKET_NAME = properties.getProperty("AWS_S3FS_BUCKET_NAME");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	public static AmazonS3 getAWSS3Conection() {
			AWS_SECRET_KEY = props.getAwsSecretKey();
			AWS_ACCESS_KEY = props.getAwsAccessKey();
		if (awsConnection == null) {
			AWSCredentials awsCredentials = new BasicAWSCredentials(
					AWS_ACCESS_KEY, AWS_SECRET_KEY);
			ClientConfiguration clientConfiguration = new ClientConfiguration();
			clientConfiguration.setProtocol(Protocol.HTTP);
			awsConnection = new AmazonS3Client(awsCredentials,
					clientConfiguration);
			//awsConnection.setEndpoint("didalio.com");
		}
		return awsConnection;
	}

	/**
	 * @author prabakaran
	 * @param String imageString, String imageType,
			String imagePath, String imageName
	 * @return String
	 * @throws Exception 
	 * 
	 */
	public static JSONObject uploadImageToS3(String imageString, String imageType,
			String imagePath, String imageName) throws Exception {
		JSONObject res = new JSONObject();
		res.put("isUploaded", false);
		try {
			BUCKET_NAME = props.getAwsBucketName();
			
			AmazonS3 amazonS3 = getAWSS3Conection();
			byte[] byeImage = Base64.decodeBase64(imageString.getBytes());
			ByteArrayInputStream in = new ByteArrayInputStream(byeImage);
			imagePath = imagePath + imageName + "." + imageType;
			PutObjectResult result = amazonS3.putObject(new PutObjectRequest(BUCKET_NAME,
					imagePath, in, new ObjectMetadata()));
			log.info("After Insert : " + result.getVersionId());
			GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(
					BUCKET_NAME, imagePath);
			//imgUrl = amazonS3.generatePresignedUrl(request).toString();
			res.put("s3fsUrl", amazonS3.generatePresignedUrl(request).toString());
			log.info("generatePresignedUrl : " + amazonS3.generatePresignedUrl(request));
			res.put("isUploaded", true);
			in.close();
		} catch (AmazonServiceException ase) {
			log.error(ase.getMessage());
			log.error("Caught an AmazonServiceException, which " +
					"means your request made it " +
					"to Amazon S3, but was rejected with an error response" +
					" for some reason.");
			log.error("Error Message:    " + ase.getMessage());
			log.error("HTTP Status Code: " + ase.getStatusCode());
			log.error("AWS Error Code:   " + ase.getErrorCode());
			log.error("Error Type:       " + ase.getErrorType());
			log.error("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			log.error(ace.getMessage());
			log.error("Caught an AmazonClientException, which " +
					"means the client encountered " +
					"an internal error while trying to " +
					"communicate with S3, " +
					"such as not being able to access the network.");
		}catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return res;
	}

	/**
	 * This method to remove image in S3FS
	 * @author prabakaran
	 * @param imagePath
	 * @return boolean
	 * 
	 */
	public static boolean removeImageInS3(String imagePath) {
		boolean success = false;
		AmazonS3 amazonS3 = getAWSS3Conection();
		try {
			BUCKET_NAME = props.getAwsBucketName();
			amazonS3.deleteObject(BUCKET_NAME , imagePath);
			success = true;
		} catch (Exception e) {
			log.error(e.getMessage());
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * 
	 * @author prabakaran
	 * @param imagePath
	 * @return boolean
	 * @throws Exception 
	 * 
	 */
	public static InputStream readImageFromS3(String imagePath) throws Exception {
		AmazonS3 amazonS3 = getAWSS3Conection();
		InputStream inputStream ;
		try {
			BUCKET_NAME = props.getAwsBucketName();
			S3Object s3object = amazonS3.getObject(new GetObjectRequest(
					BUCKET_NAME, imagePath));
			inputStream = s3object.getObjectContent();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return inputStream;
	}
	public static String getImageUrlFromS3(String imagePath) throws Exception {
		AmazonS3 amazonS3 = getAWSS3Conection();
		String imageUrl ;
		try {
			BUCKET_NAME = props.getAwsBucketName();
			Calendar date = Calendar.getInstance();
			long t= date.getTimeInMillis();
			Date afterAddingTenMins=new Date(t + (10 * 60000));
			imageUrl = amazonS3.generatePresignedUrl(BUCKET_NAME, imagePath, afterAddingTenMins).toString();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return imageUrl;
	}
}
