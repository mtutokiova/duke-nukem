package org.mule.modules.dukenukem.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DukeNukemAddEntitlementJsonResponse implements Serializable {

	private static final long serialVersionUID = -3456866855805350492L;

	@JsonProperty
	private boolean success;
	
	@JsonProperty
	private String eid;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

}
