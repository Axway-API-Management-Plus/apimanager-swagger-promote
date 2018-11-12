package com.axway.apim.swagger.api;

import com.axway.apim.lib.AppException;
import com.axway.apim.swagger.api.properties.APIAuthentication;
import com.axway.apim.swagger.api.properties.APIImage;
import com.axway.apim.swagger.api.properties.APISwaggerDefinion;

public interface IAPIDefinition {
	
	public final static String STATE_PUBLISHED = "published";
	public final static String STATE_UNPUBLISHED = "unpublished";
	public final static String STATE_DEPRECATED = "deprecated";
	public final static String STATE_DELETED = "deleted";
	
	public String getApiVersion();
	
	public String getApiPath() throws AppException;
	
	public String getStatus();
	
	public void setStatus(String status) throws AppException;

	public APIAuthentication getAuthentication() throws AppException;
	
	public boolean isValid();
	
	public String getOrgId() throws AppException;
	
	public String getApiName();
	
	public String getApiSummary();
	
	public String getApiId() throws AppException;
	
	public APIImage getApiImage();
	
	public APISwaggerDefinion getSwaggerDefinition();
	
	/**
	 * Compare the actual API-Definition with the given in Def
	 * 
	 * A list of differences is returned, representing the actual differences. 
	 * The returned list contains APIProperty which make knows, if the property is
	 * - changeable on an existing API
	 * - leads to a breaking change
	 * - is non breaking, but would require a new API-Import (ZDD)
	 * 
	 * This List will be used as a working queue in the API-Manager Adapter to 
	 * bring the API-Manager API into the desired state.
	 * @param def
	 * @return
	 */
//	public APIChangeState getChanges(APIDefinition def);
	
	//....
}