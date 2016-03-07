package org.mule.modules.dukenukem.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DukeNukemGetUserJsonResponse implements Serializable {

	private static final long serialVersionUID = 4835677115251092432L;

	@JsonProperty
	private boolean valid;
	
	@JsonProperty(value="userjson")
	private String userJson;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getUserJson() {
		return userJson;
	}

	public void setUserJson(String userJson) {
		this.userJson = userJson;
	}
	
}
