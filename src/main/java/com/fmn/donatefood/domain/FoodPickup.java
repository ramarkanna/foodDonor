package com.fmn.donatefood.domain;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmn.donatefood.domain.audit.AuditFields;
import com.fmn.donatefood.service.ImageService;

import com.fmn.embedded.Image;

public class FoodPickup extends AuditFields {
	
	@Id
	private String pickupId;
	public String getPickupId() {
		return pickupId;
	}
	public void setPickupId(String pickupId) {
		this.pickupId = pickupId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getWhenFoodMade() {
		return whenFoodMade;
	}
	public void setWhenFoodMade(String whenFoodMade) {
		this.whenFoodMade = whenFoodMade;
	}
	public String getPickupDone() {
		return pickupDone;
	}
	public void setPickupDone(String pickupDone) {
		this.pickupDone = pickupDone;
	}
	public String getNgoId() {
		return ngoId;
	}
	public void setNgoId(String ngoId) {
		this.ngoId = ngoId;
	}
	private String Name;
	private String address;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String email;
	private String phone;
	private String imageUrl;
	private String description;
	private String pickupTime;
	private Date pickupDt;
	public Date getPickupDt() {
		return pickupDt;
	}
	public void setPickupDt(Date pickupDt) {
		this.pickupDt = pickupDt;
	}
	
    private String pickupDate;
	private String servings;
	private String whenFoodMade;
	
	private String pickupDone; 
	
	private String ngoId; 
	
	@Transient
	private Image  image; 
		
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getServings() {
		return servings;
	}
	public void setServings(String servings) {
		this.servings = servings;
	}

	@Override
	public String toString() {
		return "FoodPickup [pickupId=" + pickupId + ", Name=" + Name + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", zipcode=" + zipcode + ", email=" + email + ", phone="
				+ phone + ", imageUrl=" + imageUrl + ", description=" + description + ", pickupTime=" + pickupTime
				+ ", pickupDate=" + pickupDate + ", servings=" + servings + "]";
	}

}
