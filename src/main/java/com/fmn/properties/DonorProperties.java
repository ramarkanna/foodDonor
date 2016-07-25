package com.fmn.properties;

public class DonorProperties {
	
	private static DonorProperties probs = new DonorProperties();
	
	private DonorProperties(){
	
	}
	
	public static DonorProperties getInstance(){
		return probs;
	}

	public String getHosturl() {
		return hosturl;
	}

	public void setHosturl(String hosturl) {
		this.hosturl = hosturl;
	}

	private String hosturl;
	private String searchHubDistance;
	
	private String cusomerAppBaseUrl;
	
	private String customerAppPasswordResetUrl;
	
	private String awsSecretKey;
	
	private String awsAccessKey;
	
	private String awsBucketName;
	
	public String getGap12FileName() {
		return gap12FileName;
	}

	public void setGap12FileName(String gap12FileName) {
		this.gap12FileName = gap12FileName;
	}

	public String getGaViewId() {
		return gaViewId;
	}

	public void setGaViewId(String gaViewId) {
		this.gaViewId = gaViewId;
	}

	public String getGaServiceMail() {
		return gaServiceMail;
	}

	public void setGaServiceMail(String gaServiceMail) {
		this.gaServiceMail = gaServiceMail;
	}

	private String gap12FileName;
	
	private String gaViewId;
	
	private String gaServiceMail;

	
	
	public String getElasticUrlAddress() {
		return elasticUrlAddress;
	}

	public void setElasticUrlAddress(String elasticUrlAddress) {
		this.elasticUrlAddress = elasticUrlAddress;
	}

	private String elasticUrlAddress;
	
	public String getSearchHubDistance() {
		return searchHubDistance;
	}

	public void setSearchHubDistance(String searchHubDistance) {
		this.searchHubDistance = searchHubDistance;
	}

	public String getCusomerAppBaseUrl() {
		return cusomerAppBaseUrl;
	}

	public void setCusomerAppBaseUrl(String cusomerAppBaseUrl) {
		this.cusomerAppBaseUrl = cusomerAppBaseUrl;
	}

	public String getCustomerAppPasswordResetUrl() {
		return customerAppPasswordResetUrl;
	}

	public void setCustomerAppPasswordResetUrl(String customerAppPasswordResetUrl) {
		this.customerAppPasswordResetUrl = customerAppPasswordResetUrl;
	}

	public String getAwsSecretKey() {
		return awsSecretKey;
	}

	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}

	public String getAwsAccessKey() {
		return awsAccessKey;
	}

	public void setAwsAccessKey(String awsAccessKey) {
		this.awsAccessKey = awsAccessKey;
	}

	public String getAwsBucketName() {
		return awsBucketName;
	}

	public void setAwsBucketName(String awsBucketName) {
		this.awsBucketName = awsBucketName;
	}
	
}
