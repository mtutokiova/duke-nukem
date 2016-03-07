package org.mule.modules.dukenukem;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.modules.dukenukem.client.DukeNukemClient;
import org.mule.modules.dukenukem.config.ConnectorConfig;
import org.mule.modules.dukenukem.entities.GetUserDetailsResponse;
import org.mule.modules.dukenukem.exception.DukeNukemConnectorException;

@Connector(name="duke-nukem", friendlyName="DukeNukem")
public class DukeNukemConnector {

	@Config
    ConnectorConfig config;
    
    private DukeNukemClient client;
    
    @Start
    public void init() {
        setClient(new DukeNukemClient(config));
    }

    /**
     * Custom processor to call API3 economist.getAuthorized endpoint
     *
     * @param email user email
     * @param password user password
     * @return The response of economist.getAuthorized call
     * @throws DukeNukemConnectorException 
     */
    @Processor
	public String getAuthorized(String email, String password) throws DukeNukemConnectorException{
		return getClient().getAuthorized(email, password);
	}
    
    /**
     * Custom processor to call economist.getUserDetails endpoint
     *
    * @param email user email
     * @param password user password
     * @return The response of economist.getUserDetails call
     * @throws DukeNukemConnectorException 
     */
    @Processor
	public GetUserDetailsResponse getUserDetails(String email, String password) throws DukeNukemConnectorException{
		return getClient().getUserDetails(email, password);
	}

    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

	public DukeNukemClient getClient() {
		return client;
	}

	public void setClient(DukeNukemClient client) {
		this.client = client;
	}

}