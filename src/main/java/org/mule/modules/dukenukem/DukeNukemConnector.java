package org.mule.modules.dukenukem;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.modules.dukenukem.client.DukeNukemClient;
import org.mule.modules.dukenukem.config.ConnectorConfig;
import org.mule.modules.dukenukem.exception.DukeNukemConnectorException;

@Connector(name="duke-nukem", friendlyName="DukeNukem")
public class DukeNukemConnector {
	
	public enum Gender{
		F,
		M
	}
	
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
     * @return The response of economist.getUserDetails call
     * @throws DukeNukemConnectorException 
     */
    @Processor
	public String getUserDetails(String email) throws DukeNukemConnectorException{
		return getClient().getUserDetails(email);
	}
    
    /**
     * Custom processor to call economist.getEmailStatus endpoint
     *
    * @param email user email
     * @return The response of economist.getEmailStatus call
     * @throws DukeNukemConnectorException 
     */
    @Processor
	public String getEmailStatus(String email) throws DukeNukemConnectorException{
		return getClient().getEmailStatus(email);
	}
    
    /**
     * Custom processor to call economist.addUser endpoint
     *
     * @return The response of economist.addUser call
     * @throws DukeNukemConnectorException 
     */
    @Processor
	public String addUser(String email, String password, String countryCode, Gender gender, String yearOfBirth, String firstName, String surname, Boolean emailOnline, Boolean emailGroupCompanies) throws DukeNukemConnectorException{
		return getClient().addUser(email, password, countryCode, gender, yearOfBirth, firstName, surname, emailOnline, emailGroupCompanies);
	}
    
    /**
     * Custom processor to call economist.addEntitlement endpoint
     *
     * @return The response of economist.addEntitlement call
     * @throws DukeNukemConnectorException 
     */
    @Processor
	public String addEntitlement(String email, String productCode, String termCode, String promoCode, String startDate, String endDate, String orderId) throws DukeNukemConnectorException{
		return getClient().addEntitlement(email, productCode, termCode, promoCode, startDate, endDate, orderId);
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