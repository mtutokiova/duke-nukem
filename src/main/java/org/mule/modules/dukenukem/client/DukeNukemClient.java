package org.mule.modules.dukenukem.client;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.mule.modules.dukenukem.DukeNukemConnector.Gender;
import org.mule.modules.dukenukem.config.ConnectorConfig;
import org.mule.modules.dukenukem.entities.DukeNukemGetApplicationTokenResponse;
import org.mule.modules.dukenukem.entities.DukeNukemGetUserJsonResponse;
import org.mule.modules.dukenukem.exception.DukeNukemBusinessException;
import org.mule.modules.dukenukem.exception.DukeNukemConnectorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Client implementation for calling DukeNukem endpoints.
 * @author martinatutokiova
 */

public class DukeNukemClient {

	private static final String ECONOMIST_ADD_USER = "economist.addUser";
	private static final String ECONOMIST_ADD_ENTITLEMENT = "economist.addEntitlement";
	private static final String ECONOMIST_GET_APPLICATION_TOKEN = "economist.getApplicationToken";
	private static final String ECONOMIST_GET_AUTHORIZED = "economist.getAuthorized";
	private static final String ECONOMIST_GET_EMAIL_STATUS = "economist.getEmailStatus";
	private static final String ECONOMIST_GET_USER_DETAILS = "economist.getUserDetails";
    private static final String ECONOMIST_GET_USER_JSON = "economist.getUserJson";

    private static ObjectMapper jsonObjectMapper= new ObjectMapper();

    // set initial last update of token to two days before
	private static long lastUpdatedTsSeconds = System.currentTimeMillis()/1000 - TimeUnit.DAYS.toSeconds(2);
	private static String token;
	
    private final ConnectorConfig connectorConfig;
	private final WebResource webResource;
	private final WebResource legacyWebResource;
	
	public DukeNukemClient(ConnectorConfig connectorConfig) {
        this.connectorConfig = connectorConfig;
        
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        this.webResource = Client.create(clientConfig).resource(getDukeNukemUrl());
        this.legacyWebResource = Client.create(clientConfig).resource(getLegacyDukeNukemUrl());
	}
	
	/** Returns response from the DukeNukem economist.getAuthorized call */
	public String getAuthorized(String email, String password) throws DukeNukemConnectorException {
		try {
			return getValidatedResponse(webResource
					.path(ECONOMIST_GET_AUTHORIZED)
					.queryParam("token", getApplicationToken())
					.queryParam("ts", Long.toString(lastUpdatedTsSeconds))
					.queryParam("p", getHashedPassword(password))
					.queryParam("e", email)
					.get(ClientResponse.class));
		} catch (DukeNukemBusinessException e) {
			return e.getMessage();
		}
	}
	
	/** Returns response from the DukeNukem economist.getUserDetails call */
	public String getUserDetails(String email) throws DukeNukemConnectorException {
		try {
			return getValidatedResponse(webResource
					.path(ECONOMIST_GET_USER_DETAILS)
					.queryParam("token", getApplicationToken())
					.queryParam("ts", Long.toString(lastUpdatedTsSeconds))
					.queryParam("u", URLEncoder.encode(getUserJson(email), "UTF-8"))
					.get(ClientResponse.class));
		} catch (IOException | ClientHandlerException | UniformInterfaceException e) {
			throw new DukeNukemConnectorException(e.getMessage());
		} catch (DukeNukemBusinessException e) {
			return e.getMessage();
		}
	}
	
	/** Returns response from the DukeNukem economist.getEmailStatus call */
	public String getEmailStatus(String email) throws DukeNukemConnectorException {
		try {
			return getValidatedResponse(legacyWebResource
					.path(ECONOMIST_GET_EMAIL_STATUS)
					.queryParam("token", getApplicationToken())
					.queryParam("ts", Long.toString(lastUpdatedTsSeconds))
					.queryParam("e", email)
					.get(ClientResponse.class));
		} catch (DukeNukemBusinessException e) {
			return e.getMessage();
		}
	}
	
	/** Returns response from the DukeNukem economist.addUser call */
	public String addUser(String email, String password, String countryCode, Gender gender, String yearOfBirth, String firstName, String surname, Boolean emailOnline, Boolean emailGroupCompanies) throws DukeNukemConnectorException {
		try {
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("token", getApplicationToken());
			formData.add("ts", Long.toString(lastUpdatedTsSeconds));
			formData.add("e", email);
			formData.add("p", password);
			formData.add("c", countryCode);
			formData.add("g", gender != null ? gender.name() : null);
			formData.add("y", yearOfBirth);
			formData.add("gn", firstName);
			formData.add("fn", surname);
			formData.add("eo", emailOnline ? "Y" : "N");
			formData.add("eg", emailGroupCompanies ? "Y" : "N");

			return getValidatedResponse(legacyWebResource
					.path(ECONOMIST_ADD_USER)
					.post(ClientResponse.class, formData));
			
		} catch (DukeNukemBusinessException e) {
			return e.getMessage();
		}
	}
	
	/** Returns response from the DukeNukem economist.addEntitlement call */
	public String addEntitlement(String email, String productCode, String termCode, String promoCode, String startDate, String endDate, String orderId) throws DukeNukemConnectorException{
		try {
			MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			formData.add("token", getApplicationToken());
			formData.add("ts", Long.toString(lastUpdatedTsSeconds));
			formData.add("e", email);
			formData.add("u", getUserJson(email));
			formData.add("product", productCode);
			formData.add("term", termCode);
			formData.add("promo", promoCode);
			formData.add("start", startDate);
			formData.add("end", endDate);
			formData.add("orderid", orderId);

			return getValidatedResponse(legacyWebResource
					.path(ECONOMIST_ADD_ENTITLEMENT)
					.post(ClientResponse.class, formData));
			
		} catch (DukeNukemBusinessException e) {
			return e.getMessage();
		}
	}
	
	/////////////
	/// UTILS ///
	/////////////
	
	/** Returns the user data for a user with the given email in json format which is required for other endpoints */
	private String getUserJson(String email) throws DukeNukemConnectorException, DukeNukemBusinessException {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("token", getApplicationToken());
		formData.add("ts", Long.toString(lastUpdatedTsSeconds));
		formData.add("e", email);
		
		ClientResponse clientResponse = legacyWebResource
				.path(ECONOMIST_GET_USER_JSON)
				.post(ClientResponse.class, formData);
		
		String responseString = getValidatedResponse(clientResponse);
		
		try {
			return jsonObjectMapper.readValue(responseString, DukeNukemGetUserJsonResponse.class).getUserJson();
		} catch (IOException e) {
			throw new DukeNukemConnectorException(e.getMessage());
		}
	}

	/** Returns an active application token */
	private String getApplicationToken() throws DukeNukemConnectorException, DukeNukemBusinessException {
		if(System.currentTimeMillis()/1000 - lastUpdatedTsSeconds >= TimeUnit.HOURS.toSeconds(23)){
			requestNewToken();
		} 
		return token;
    }
	
	/** Request new token from DukeNukem */
	private void requestNewToken() throws DukeNukemConnectorException, DukeNukemBusinessException {
		DukeNukemClient.lastUpdatedTsSeconds = System.currentTimeMillis()/1000;
		DukeNukemClient.token = getNewApplicationToken();
	}

	/** Returns new application token */
	private String getNewApplicationToken() throws DukeNukemConnectorException, DukeNukemBusinessException {
		WebResource request = webResource
				.path(ECONOMIST_GET_APPLICATION_TOKEN)
				.queryParam("id", generateHashedValue(connectorConfig.getThirdPartyId() + Long.toString(lastUpdatedTsSeconds)))
				.queryParam("ts", Long.toString(lastUpdatedTsSeconds));
		
		System.out.println("Sending request: " + request);
		
		ClientResponse clientResponse = request
				.get(ClientResponse.class);
		
		for (Entry<String, Object> property : clientResponse.getProperties().entrySet()) {
			System.out.println("property " + property.getKey() + ": " + property.getValue());	
		}
		
		String responseString = getValidatedResponse(clientResponse);
		
		try {
			return jsonObjectMapper.readValue(responseString, DukeNukemGetApplicationTokenResponse.class).getToken();
		} catch (IOException e) {
			throw new DukeNukemConnectorException(e.getMessage());
		}
	}

	/** Returns the response string in case of valid response or throws an exception in case of invalid response */
	private String getValidatedResponse(ClientResponse clientResponse) throws DukeNukemConnectorException, DukeNukemBusinessException {
		String responseEntity = clientResponse.getEntity(String.class);
		if(clientResponse.getStatus() != 200 ){
			throw new DukeNukemConnectorException(responseEntity);
		} else if (responseEntity.equals("false") || responseEntity.contains("\"valid\":false")){
			throw new DukeNukemBusinessException(responseEntity);
		}
		return responseEntity;
	}
	
	/** Returns hashed password */
	private String getHashedPassword(String password) throws DukeNukemConnectorException, DukeNukemBusinessException {
		return generateHashedValue(generateHashedValue(password) + "." + getApplicationToken());
	}
	
	/** Returns upper cased hashed value for the input string */
	private String generateHashedValue(String input) {
		return DigestUtils.md5Hex(input).toUpperCase();
	}

	/** Returns the URL to DukeNukem server */
	private String getDukeNukemUrl() {
		return "https://" + connectorConfig.getHost() + "/" + connectorConfig.getVersionNumber();
	}
	
	/** Returns the legacy URL to DukeNukem server */
	private String getLegacyDukeNukemUrl() {
		return "http://" + connectorConfig.getHost() + "/" + "2.0";
	}
	
	public static void main(String[] args) throws DukeNukemConnectorException, DukeNukemBusinessException {
		ConnectorConfig connectorConfig = new ConnectorConfig();
		connectorConfig.setHost("stage.economist.com/api");
		connectorConfig.setThirdPartyId("a73fc5db8b93b5e401b7b5458ff776a1");
		connectorConfig.setVersionNumber("3.0");
		
		System.out.println((new Date()).getTime()/1000);
		
		DukeNukemClient dukeNukemClient = new DukeNukemClient(connectorConfig);
		System.out.println(dukeNukemClient.getApplicationToken() + " " + DukeNukemClient.lastUpdatedTsSeconds);
//		System.out.println(dukeNukemClient.getAuthorized("martina.test1234567@globallogic.com", ""));
		System.out.println(dukeNukemClient.getUserJson("martina.tutokiova@globallogic.com"));
//		System.out.println(dukeNukemClient.getUserDetails("martina.tutokiova@globallogic.com", "orange1234"));
//		System.out.println(dukeNukemClient.getEmailStatus("martina.tutokiova@globallogic.com"));
//		System.out.println(dukeNukemClient.addUser("martina.tuto@globallogic.com", "orange1234", "SK", Gender.M, "1986", "Martina", "Tut", YesNoEnum.Y, YesNoEnum.N));
//		System.out.println(dukeNukemClient.getUserDetails("martina.tuto@globallogic.com"));
//		System.out.println(dukeNukemClient.addEntitlement("martina.tutokiova@globallogic.com", "A", "ABC", "PROMO", "1458815627", "", ""));
	}

}
