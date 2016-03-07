package org.mule.modules.dukenukem.config;

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {

	@Configurable
    @Default(value="www.economist.com/api")
    private String host;

    @Configurable
    @Default(value="3.0")
    private String versionNumber;
    
    @Configurable
    private String thirdPartyId;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}
	
}