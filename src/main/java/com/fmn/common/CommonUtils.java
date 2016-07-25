package com.fmn.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;


public class CommonUtils {
	
	// Find your Account Sid and Token at twilio.com/user/account 
	public static final String ACCOUNT_SID = "ACd3848dccc4efc102eb1466418ce31162"; 
	public static final String AUTH_TOKEN = "6a4974cb0332030a95227fa642568c5a"; 
	public static final String MESSAGING_SERVICE_ID = "MGf8850a991c223f83e09f491a3e6a8e88"; 
	public static final String POSTMAN_API_KEY = "0d91957c-a072-4aa7-97aa-e3dd2c8305ea";
	public  static final String POSTMAN_API_URL="https://api.postmarkapp.com/email/withTemplate";

	public static final String ORDER_TEMPL = "714921";
    public static final String CONTACTUS_TEMPL = "715062";
    public static final String SIGNUP_TEMPL = "719481";
    public static final String RESETPSSWD_TEMPL = "719584";
    public static final String SPECIALOOFFER_TEMPL = "721641";
    public static final String TRYOFFER_TEMPL = "723609";
    public static final String ORDERCANCEL_TEMPL = "723921";
    public static final String PARTNER_TEMPL = "742821";
    public static final String SHOPQUOTE_TEMPL = "746081";
    public static final String NGO_PICKUP_TEMPL = "786242";
    public static final String REWARD_SIGNUP_TEMPL = "795122";

    
    
    
    public static final String POSTMAN_FROM_ADDR="Shreya@freshmealsnow.com";
    
    
        
    private static Logger logger = Logger.getLogger(CommonUtils.class.getName());

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}
	
	public static double convertDistanceUnit(double dist, String fUnit, String tUnit){
		fUnit = fUnit.trim().toUpperCase();
		tUnit = tUnit.trim().toUpperCase();
		
		if(fUnit.equals(tUnit))
			return dist;
		
		if(fUnit.equals("K"))
			dist = dist / 1.609344;
		else if(fUnit.equals("N"))
			dist = dist / 0.8684;
		
		if (tUnit.equals("K")) {
			dist = dist * 1.609344;
		} else if (tUnit.equals("N")) {
			dist = dist * 0.8684;
		}
		
		return dist;
	}

	//	This function converts decimal degrees to radians		
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	//	This function converts radians to decimal degrees					
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	public static boolean validateString(String value){
		if(value==null || value.trim().equals("")){
			return false;
		}
		return true;
	}
	
	public static boolean validateList(List<?> list){
		if(list==null || list.isEmpty()){
			return false;
		}
		return true;
	}
	
	public static String generateRandomInteger(int length){
	    List<Integer> numbers = new ArrayList<>();
	    for(int i = 0; i < 10; i++){
	        numbers.add(i);
	    }

	    Collections.shuffle(numbers);

	    String result = "";
	    for(int i = 0; i < length; i++){
	        result += numbers.get(i).toString();
	    }
		    
		return result;
	}
	
	public static String passwordToHash(String password) {
		if(!CommonUtils.validateString(password))
			password = "fmn@123";
	    String passwordToHash = password;
	    String generatedPassword = null;
	    try {
	      // Create MessageDigest instance for MD5
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      // Add password bytes to digest
	      md.update(passwordToHash.getBytes());
	      // Get the hash's bytes
	      byte[] bytes = md.digest();
	      // This bytes[] has bytes in decimal format;
	      // Convert it to hexadecimal format
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }
	      // Get complete hashed password in hex format
	      generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    //System.out.println(generatedPassword);
	    return generatedPassword;
	  }
	
	public static boolean sendMail(String to, String subject, String body) {
		boolean flag = false;
		
		/* SendGrid sendgrid = new SendGrid("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");

	    SendGrid.Email email = new SendGrid.Email();

	    email.addTo(to);
	    email.setFrom("prabakaran.a@mitosistech.com");
	    email.setSubject(subject);
	    email.setHtml(body);

	    try {
			SendGrid.Response response = sendgrid.send(email);
		} catch (SendGridException e) {
			e.printStackTrace();
		}*/
		
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
		Session session = Session.getDefaultInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress toAddress = new InternetAddress();
			toAddress = new InternetAddress(to);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			//message.setContent(body, "text/html");
			
			

			  // This mail has 2 part, the BODY and the embedded image
	         MimeMultipart multipart = new MimeMultipart("related");
			 BodyPart messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setContent(body, "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);

	         // second part (the image)
	         messageBodyPart = new MimeBodyPart();
	         ClassLoader classLoader = CommonUtils.class.getClassLoader();
	        // CommonUtils.class
	 		File file = new File(classLoader.getResource("images/logo-tastydash.png").getFile());
	        System.out.println("Logo image path: "+file.toString());
	 		DataSource fds = new FileDataSource(file);

	         messageBodyPart.setDataHandler(new DataHandler(fds));
	         messageBodyPart.setHeader("Content-ID", "<image>");
	         messageBodyPart.setDisposition(MimeBodyPart.INLINE);
	         // add image to the multipart
	         multipart.addBodyPart(messageBodyPart);

	         // put everything together
	         message.setContent(multipart);
			
			
			
			//message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			flag = true;
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		return flag;
	}
	
	public static boolean sendSMS(String to, String body){
		boolean result = false;
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 

		// Build the parameters 
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		params.add(new BasicNameValuePair("To", to)); 
		params.add(new BasicNameValuePair("From", "+16502002040")); 
		//params.add(new BasicNameValuePair("MessagingServiceSid", MESSAGING_SERVICE_ID));
		params.add(new BasicNameValuePair("Body", body)); 
		try{
			MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
			com.twilio.sdk.resource.instance.Message message = messageFactory.create(params); 
			result = true;
			System.out.println(message.getSid()); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static String convertDateToStringWithFormat(Date date, String format) {
	    SimpleDateFormat f = new SimpleDateFormat(format);
	    String dateString = f.format(date);
	    return dateString;
	  }

	  public static Date convertStringToDateWithFormat(String dateString, String format) {
	    SimpleDateFormat f = new SimpleDateFormat(format);
	    Date date = new Date();
	    try {
	      date = f.parse(dateString);
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }
	    return date;
	  }
	  
	  public  boolean equalLists(List<String> one, List<String> two){     
		    if (one == null && two == null){
		        return true;
		    }

		    if((one == null && two != null) 
		      || one != null && two == null
		      || one.size() != two.size()){
		        return false;
		    }

		    //to avoid messing the order of the lists we will use a copy
		    //as noted in comments by A. R. S.
		    one = new ArrayList<String>(one); 
		    two = new ArrayList<String>(two);   

		    Collections.sort(one);
		    Collections.sort(two);      
		    return one.equals(two);
		}

	  
	  public static  String getFile(String fileName) {

			StringBuilder result = new StringBuilder("");

			//Get file from resources folder
	         ClassLoader classLoader = CommonUtils.class.getClassLoader();

			
			//HubCustomerOrderServiceImpl.class
			File file = new File(classLoader.getResource(fileName).getFile());
			System.out.println("File path: "+file.toString());
			try (Scanner scanner = new Scanner(file)) {

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					result.append(line).append("\n");
				}

				scanner.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
				
			return result.toString();

		  }

	    @SuppressWarnings("unchecked")
		public static Object setAuditColumnInfo(String fullyQualifiedName,String userId)
				   throws Exception {
				  Class cls = Class.forName(fullyQualifiedName);
				  Object obj = cls.newInstance();
				  Class[] paramString = new Class[1];
				  paramString[0] = String.class;
				  // TODO:Need to get user from session and set here
				  Method method = cls.getDeclaredMethod("setCreatedby", paramString);
				  if(userId==null){
				   method.invoke(obj, "123");
				  }else{
				   method.invoke(obj,userId);
				  }
				  method = cls.getDeclaredMethod("setUpdatedby", paramString);
				  if(userId==null){
				   method.invoke(obj, "123");
				  }else{
				   method.invoke(obj,userId); 
				  }
				  Class[] paramString1 = new Class[1];
				  paramString1[0] = Date.class;
				  method = cls.getDeclaredMethod("setUpdated", paramString1);
				  method.invoke(obj, new Date());
				  method = cls.getDeclaredMethod("setCreated", paramString1);
				  method.invoke(obj, new Date());
				  /*Class[] paramString2 = new Class[1];
				  paramString2[0] = Character.class;
				  method = cls.getDeclaredMethod("setIsactive", paramString2);
				  method.invoke(obj, 'Y');*/
				  return obj;
		}

	    
	    public static void postmanMail( String data )
		{
		    String line ="";
		    StringBuffer result = new StringBuffer();

			try{
			URL url = new URL(POSTMAN_API_URL); 
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection)urlConnection;
			httpConn.setRequestProperty("Accept", "application/json");
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConn.setRequestProperty("Connection", "keep-alive");
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("X-Postmark-Server-Token", POSTMAN_API_KEY);

				//String data = "{  'TemplateId': 715062,  'TemplateModel': {'name':'Kannadhasan','issue_desc':'Poor Quality, When i was trying to order item using your online site , Server Connection Timeout message Received in the order item page.'},  'To': 'kannadhasan.r@mitosistech.com',  'From': 'Shreya@freshmealsnow.com',  'InlineCss': true}";

			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			// send request

			    OutputStreamWriter wr = new OutputStreamWriter(httpConn.getOutputStream());
			    wr.write(data.toString());
			    wr.flush();
			    wr.close();
			    
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			while ((line = rd.readLine()) != null) {
			    result.append(line);
			}
			rd.close();
			logger.log(Level.INFO,result.toString());
			
			    }catch(Exception ex){
			    	logger.log(Level.ERROR, ex.getMessage(),ex);
			    	}
			
		}
		
	    
		/**
		 * This method used for removed null and formated the date.
		 * @author Kannadhasan
		 * @return json string 
		 */
		public static String getObjectMapperWithSerializationFeature(Object obj) throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			String res = "";
			try{
				mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				mapper.setSerializationInclusion(Include.NON_NULL);
				res = mapper.writeValueAsString(obj);
			}catch(Exception e){
				e.printStackTrace();
			}
			return res;
		}

		  public static Date getLastDateOfMonth(Date date){
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(date);
		        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		        return cal.getTime();
		    }

}
