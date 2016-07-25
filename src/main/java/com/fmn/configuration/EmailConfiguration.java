package com.fmn.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfiguration {
  /* @Bean 
   public JavaMailSender getJavaMailSenderImpl(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

       String from = "prabakaran.a@mitosistech.com";
		String pass = "praba123";
		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        javaMailSender.setJavaMailProperties(props);

        return javaMailSender;
    }*/
   
   /*@Bean
   public VelocityEngine velocityEngine() throws VelocityException, IOException{
   	VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
   	Properties props = new Properties();
   	props.put("resource.loader", "class");
   	props.put("class.resource.loader.class", 
   			  "org.apache.velocity.runtime.resource.loader." + 
   			  "ClasspathResourceLoader");
   	factory.setVelocityProperties(props);
   	
   	return factory.createVelocityEngine();
   }*/

    /*@Bean
    public ClassLoaderTemplateResolver emailTemplateResolver(){
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("/templates/email");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode("HTML5");
        emailTemplateResolver.setCharacterEncoding("UTF-8");
        emailTemplateResolver.setOrder(1);

        return emailTemplateResolver;
    }*/
}