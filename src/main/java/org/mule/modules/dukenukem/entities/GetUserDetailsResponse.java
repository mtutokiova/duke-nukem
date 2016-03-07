package org.mule.modules.dukenukem.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUserDetailsResponse implements Serializable {
	
	private static final long serialVersionUID = 2639056635209366900L;

	@JsonProperty
	private String penname;
	
	@JsonProperty(value="first_name")
	private String firstName;
	
	@JsonProperty
	private String surname;
	
	@JsonProperty(value="iso_country_id")
	private String isoCountryId;
	
	@JsonProperty()
	private boolean isBlocked;
	
	@JsonProperty(value="email_address")
	private String emailAddress;
	
	@SuppressWarnings("rawtypes")
	@JsonProperty(value="crn_codes")
	private List crnCodes;
	
	@JsonProperty(value="access_level")
	private int accessLevel;
	
	@SuppressWarnings("rawtypes")
	@JsonProperty
	private List bundles;
	
	@JsonProperty(value="accept_mail_from_group")
	private String acceptMailFromGroup;
	
	@JsonProperty(value="accept_mail_from_eiu")
	private String acceptMailFromEiu;
	
	@JsonProperty(value="accept_mail_from_dotcom")
	private String acceptMailFromDotcom;

	public String getPenname() {
		return penname;
	}

	public void setPenname(String penname) {
		this.penname = penname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getIsoCountryId() {
		return isoCountryId;
	}

	public void setIsoCountryId(String isoCountryId) {
		this.isoCountryId = isoCountryId;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public List getCrnCodes() {
		return crnCodes;
	}

	public void setCrnCodes(List crnCodes) {
		this.crnCodes = crnCodes;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public List getBundles() {
		return bundles;
	}

	public void setBundles(List bundles) {
		this.bundles = bundles;
	}

	public String getAcceptMailFromGroup() {
		return acceptMailFromGroup;
	}

	public void setAcceptMailFromGroup(String acceptMailFromGroup) {
		this.acceptMailFromGroup = acceptMailFromGroup;
	}

	public String getAcceptMailFromEiu() {
		return acceptMailFromEiu;
	}

	public void setAcceptMailFromEiu(String acceptMailFromEiu) {
		this.acceptMailFromEiu = acceptMailFromEiu;
	}

	public String getAcceptMailFromDotcom() {
		return acceptMailFromDotcom;
	}

	public void setAcceptMailFromDotcom(String acceptMailFromDotcom) {
		this.acceptMailFromDotcom = acceptMailFromDotcom;
	}

	@Override
	public String toString() {
		return "GetUserDetailsResponse [penname=" + penname + ", firstName="
				+ firstName + ", surname=" + surname + ", isoCountryId="
				+ isoCountryId + ", isBlocked=" + isBlocked + ", emailAddress="
				+ emailAddress + ", crnCodes=" + crnCodes + ", accessLevel="
				+ accessLevel + ", bundles=" + bundles
				+ ", acceptMailFromGroup=" + acceptMailFromGroup
				+ ", acceptMailFromEiu=" + acceptMailFromEiu
				+ ", acceptMailFromDotcom=" + acceptMailFromDotcom + "]";
	}
}
