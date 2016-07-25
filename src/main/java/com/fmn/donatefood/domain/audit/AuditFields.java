package com.fmn.donatefood.domain.audit;

import java.util.Date;

public class AuditFields {

	// Dates when the Entity record was created/ modified.
	private Date createOn;
	
	private String createBy;
	
	private Date modifiedOn;
	
	private String modifiedBy;
	
	// User can be Active (true) or Soft deleted (false)
	private boolean isActive;
	
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
