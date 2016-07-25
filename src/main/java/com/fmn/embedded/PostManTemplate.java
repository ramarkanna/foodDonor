package com.fmn.embedded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostManTemplate {
//	String data = "{  'TemplateId': " +CommonUtils.CONTACTUS_TEMPL +",  'TemplateModel': {'name':'"+cusName+"','issue_desc':'"+contactus.getIssue()+"'},  'To': '"+emailId+"',  'From': 'Shreya@freshmealsnow.com',  'InlineCss': true}";
	
	private String TemplateId ;
	public String getTemplateId() {
		return TemplateId;
	}

	public void setTemplateId(String orderTempl) {
		TemplateId = orderTempl;
	}

	public Map getTemplateModel() {
		return TemplateModel;
	}

	public void setTemplateModel(Map templateModel) {
		TemplateModel = templateModel;
	}

	public String getTo() {
		return To;
	}

	public void setTo(String to) {
		To = to;
	}

	public String getFrom() {
		return From;
	}

	public void setFrom(String from) {
		From = from;
	}

	public boolean isInlineCss() {
		return InlineCss;
	}

	public void setInlineCss(boolean inlineCss) {
		InlineCss = inlineCss;
	}

	private Map TemplateModel = new HashMap();
	private String To;
	private String From;
	public String getCc() {
		return Cc;
	}

	public void setCc(String cc) {
		Cc = cc +"";
	}

	public String getBcc() {
		return Bcc +"";
	}

	public void setBcc(String bcc) {
		Bcc = bcc;
	}

	private String Cc;
	private String Bcc;
	
	private boolean InlineCss= true;
	
}
