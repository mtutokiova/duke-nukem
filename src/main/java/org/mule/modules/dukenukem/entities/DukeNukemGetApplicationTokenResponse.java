package org.mule.modules.dukenukem.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DukeNukemGetApplicationTokenResponse implements Serializable {

	private static final long serialVersionUID = 1107600306147571595L;

	@JsonProperty
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
