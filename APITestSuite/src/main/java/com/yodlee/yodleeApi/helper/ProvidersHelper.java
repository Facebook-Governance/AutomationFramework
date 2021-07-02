/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.helper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.NonSdgLoginForm;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.commonUtils.EncryptionUtil;

import io.restassured.response.Response;

/**
 * Singleton class Specifically for SDG FLOW that gets all the sites from the
 * environment and creates data structure[providerObject, LoginForm,
 * SimplifiedLoginForm and FieldArray ] which can be used to add account.
 *
 * @author Soujanya Kokala and Rahul Kumar
 */
public class ProvidersHelper {


	/**
	 * Creates a JSONObject - key is Constants.LOGIN_FORM and the value is loginForm
	 * param
	 *
	 * @param loginForm
	 * @return JSONObject
	 * @throws JSONException
	 */
	public JSONObject createLoginFormObject(JSONObject loginForm) throws JSONException {

		JSONObject loginFormObject = new JSONObject();
		loginFormObject.put(Constants.LOGIN_FORM, loginForm);
		return loginFormObject;
	}

	

	public String trimLength(String credentials, int maxLength) {
		try {
			return credentials.substring(0, maxLength);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return credentials;
	}

	/**
	 * Create simplified Login Form Object.
	 *
	 * @param rowObject
	 * @return
	 * @throws JSONException
	 */

	public JSONObject createSimplifiedLoginFormObject(JSONArray rowObject) throws JSONException {
		JSONObject loginForm = new JSONObject();
		JSONObject row = new JSONObject();
		System.out.println("RowArray Object :" + rowObject);
		row.put(Constants.ROW, rowObject);
		loginForm.put(Constants.LOGIN_FORM, row);
		return loginForm;
	}

	/**
	 * For SDG only: Update simplified login form with userName and password.
	 *
	 * @param loginFormJson
	 *            login form object
	 * @param rowLength
	 *            row length
	 * @param credentials
	 *            credentials details.
	 * @return
	 * @throws JSONException
	 */
	public String updateSimplifiedLoginFormWithCredentialsForSDG(JSONObject loginFormJson, int rowLength,
			String dagUsername, String dagPassword) throws JSONException {

		loginFormJson.getJSONObject(Constants.LOGIN_FORM).getJSONArray(Constants.ROW).getJSONObject(0)
				.getJSONArray(Constants.FIELD).getJSONObject(0).remove(Constants.VALUE);
		loginFormJson.getJSONObject(Constants.LOGIN_FORM).getJSONArray(Constants.ROW).getJSONObject(0)
				.getJSONArray(Constants.FIELD).getJSONObject(0).put(Constants.VALUE, dagUsername);

		loginFormJson.getJSONObject(Constants.LOGIN_FORM).getJSONArray(Constants.ROW).getJSONObject(0)
				.getJSONArray(Constants.FIELD).getJSONObject(1).remove(Constants.VALUE);
		loginFormJson.getJSONObject(Constants.LOGIN_FORM).getJSONArray(Constants.ROW).getJSONObject(0)
				.getJSONArray(Constants.FIELD).getJSONObject(1).put(Constants.VALUE, dagPassword);

		return loginFormJson.toString();

	}

	public HttpMethodParameters createInputParamforAcctAddition(Long providerId, String credentialsParam) {
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put("providerId", String.valueOf(providerId));

		HttpMethodParameters httpParms = HttpMethodParameters.builder().build();

		httpParms.setBodyParams(credentialsParam);
		httpParms.setQueryParams(queryParams);
		return httpParms;

	}

	/**
	 * Used by @API- GET /providers to form query params
	 * 
	 * @param priority
	 * @param name
	 * @param skip
	 * @param top
	 * @param dataset$filter
	 * @param capability
	 * @return
	 */
	public LinkedHashMap<String, Object> createQueryParamsForGetProviders(String priority, String name,
			String top, String dataset, String capability) {

		LinkedHashMap<String, Object> getProvidersQueryParams = new LinkedHashMap<>();

		if (name != null && !name.equalsIgnoreCase("null"))
			getProvidersQueryParams.put("name", name);

		if (top != null && !top.equalsIgnoreCase("null"))
			getProvidersQueryParams.put("top", top);

		if (priority != null && !priority.equalsIgnoreCase("null"))
			getProvidersQueryParams.put("priority", priority);

		if (capability != null && !capability.equalsIgnoreCase("null"))
			getProvidersQueryParams.put("capability", capability);

		
		if(dataset!=null && !dataset.equals("null") && Configuration.getInstance().isHybrid()) {
		getProvidersQueryParams.put("dataset$filter", dataset);
		}else if(dataset!=null && !dataset.equals("null") && !Configuration.getInstance().isHybrid()) {
			getProvidersQueryParams.put("dataSet.attribute", dataset);
			}

		System.out.println("getProvidersQueryParams::"+getProvidersQueryParams);
		return getProvidersQueryParams;
	}

	
	public Map<String, Object> createPathParamsGetProviderDetails(String siteId) {
		Map<String, Object> pathParams=new HashMap<>();
		pathParams.put("providerId", siteId);
		return pathParams;
	}


/**
	 * This method is used to create path param and return the HttpParams..
	 * 
	 * @param siteId
	 *            it is siteId/providerId
	 * @return
	 */
	public HttpMethodParameters createInputForGetProviderDetails(Long providerId) {

		
		HttpMethodParameters httpMethodParameters=HttpMethodParameters.builder().build();
		Map<String, Object> pathParams=new HashMap<String,Object>();
		pathParams.put("providerId", providerId);
		httpMethodParameters.setPathParams(pathParams);
		
		return httpMethodParameters;
	}
	
	 public String getJsonObjectValueAsString(JSONObject providerObject, String node) {

			String jsonObjectValue = null;

			try {
				jsonObjectValue = providerObject.get(node).toString();
			} catch (JSONException jsonException) {
				System.out.println(jsonException);
			}

			return jsonObjectValue;

		}
}
