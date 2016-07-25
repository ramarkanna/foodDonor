package com.fmn.configuration;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.fmn.properties.DonorProperties;


@Configuration
@PropertySource(value = "classpath:donor.properties")
public class DonorConfiguration {
	@Resource
    private Environment environment;
	
	@Bean
	public DonorProperties loadProperties(){
		DonorProperties prop = DonorProperties.getInstance();
		prop.setSearchHubDistance(environment.getProperty("donor.search.ngo.distance.miles"));
		prop.setCusomerAppBaseUrl(environment.getProperty("donor.customerapp.baseurl"));
		prop.setCustomerAppPasswordResetUrl(environment.getProperty("donor.customerapp.passwordreseturl"));
		prop.setAwsSecretKey(environment.getProperty("aws.s3fs.secret.key"));
		prop.setAwsAccessKey(environment.getProperty("aws.s3fs.access.key"));
		prop.setAwsBucketName(environment.getProperty("aws.s3fs.bucket.name"));
		prop.setHosturl(environment.getProperty("donor.hosturl"));
		
		return prop;
	}

}
