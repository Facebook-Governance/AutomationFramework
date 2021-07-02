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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.OMGVMCID;
import org.testng.Assert;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.apiUtils.DataExtractUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class DataExtractsHelper {
	
	DataExtractUtils dataExtractUtils = new DataExtractUtils();
	CobrandUtils cobrandUtils = new CobrandUtils();
	CommonUtils commonUtils = new CommonUtils();
	CobrandHelper cobrandHelper = new CobrandHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	Configuration configurationObj = Configuration.getInstance();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();

	public Response getUserDataResponseForUser(String fromDateAsString, String toDateAsString,
			Session session, String userName) {
		Response cobrandDataResponse = getCobrandData(fromDateAsString, toDateAsString, session);
		cobrandDataResponse.then().log().all();

		JSONObject cobrandDataObject = new JSONObject(cobrandDataResponse.asString());
		JSONArray userDataArray = null;
		try {
			userDataArray = cobrandDataObject.getJSONObject("event").getJSONObject("data").getJSONArray("userData");
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		String userDataHref = null;
		for (Object object : userDataArray) {
			JSONObject userDataObject = (JSONObject) object;
			if (userDataObject.getJSONObject("user").getString("loginName").equals(userName)) {
				userDataHref = userDataObject.getJSONArray("links").getJSONObject(0).getString("href");
				break;
			}
		}
		if (userDataHref == null) {
			Assert.fail("User name - " + userName + " is not listed in getCobrandData for the time period - "
					+ fromDateAsString + "," + toDateAsString);
		}
		HttpMethodParameters httpParams= HttpMethodParameters.builder().build();
		httpParams.setBodyParams(userDataHref);
		Response userDataResponse = dataExtractUtils.getUserData(httpParams, session);
		return userDataResponse;
	}
	
	public Response getCobrandData(String fromDate, String toDate, Session session) {
		Map<String, Object> queryParams = new LinkedHashMap<>();
		queryParams.put("eventName", "DATA_UPDATES");
		queryParams.put("fromDate", fromDate);
		queryParams.put("toDate", toDate);
		
		HttpMethodParameters httpParms= HttpMethodParameters.builder().build();
		httpParms.setQueryParams(queryParams);
		
		Response cobrandData = dataExtractUtils.getEvents(httpParms, configurationObj.getCobrandSessionObj());
		/*Response cobrandData = RestUtil.getRequestWithQueryParam(queryParams,
				commonEnvSession.getPath() + Constants.DATA_EXTRACTS_EVENTS_URL, commonEnvSession.getCobSession(),
				commonEnvSession.getUserSession());*/
		return cobrandData;
	}
	
	public HttpMethodParameters createQueryParamsForGetUser(String fromDateAsString, String toDateAsString,
			String userName) {
		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
		queryParam.put("fromDate", fromDateAsString);
		queryParam.put("toDate", toDateAsString);
		queryParam.put("loginName", userName);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		return httpParams;
		
	}
	
	
	/**
	 * This method can be used in both SDG and NON-SDG, if path param has to be
	 * formed with only one parameter.
	 * 
	 * @param Firstkey
	 *            it is a key
	 * @param Firstvalue
	 *            it is a value that needs to be assigned for that key
	 * @return
	 */
	public HttpMethodParameters createPathParam(String Firstkey, Object Firstvalue) {
		LinkedHashMap<String, Object> pathParam = new LinkedHashMap<String, Object>();
		pathParam.put(Firstkey, Firstvalue);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		return httpParams;
	}
	
	/*public Response subscriptionMethods(Session envSession, String Event, String eventType,
			String key, String... CallBackURL) throws IOException {

		HashMap<String, String> callBackURLMap = new HashMap<>();
		JSONObject bodyParam = new JSONObject();
		String bodyParamAsString = null;

		if (CallBackURL.length != 0) {
			callBackURLMap.put("callbackUrl", CallBackURL[0]);
			bodyParam.put("event", callBackURLMap);
			bodyParamAsString = bodyParam.toString();
		}

		Map<String, String> eventMap = new HashMap<>();
		eventMap.put("eventName", Event);

		String url = configurationObj.getCobrandSessionObj().getPath() + Constants.NOTIFICATION_SUBSCRIPTION_EVENTS_URL;
		String GETUrl = configurationObj.getCobrandSessionObj().getPath() + Constants.NOTIFICATION_EVENTS_URL;
		Response response = null;

		switch (eventType) {
		case "CREATE": {
			if (key.equalsIgnoreCase("valid")) {
				response = RestUtil.postWithPathParamAndBodyAndCobSession(bodyParamAsString, eventMap, url,
						envSession.getCobSession(), "application/json");
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession Session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				response =cobrandUtils.createNotificationEvent(httpParams, Session);
				break;
			} else if (key.equalsIgnoreCase("invalidCobSession")) {
				String invalidCobSession = configurationObj.getCobrandSessionObj().getCobSession() + "azs99";
				response = RestUtil.postWithPathParamAndBodyAndCobSession(bodyParamAsString, eventMap, url,
						invalidCobSession, "application/json");
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession Session = new EnvSession(invalidCobSession,
						configurationObj.getCobrandSessionObj().getPath());
				response =cobrandUtils.createNotificationEvent(httpParams, Session);
				break;
			} else if (key.equalsIgnoreCase("nullCobSession")) {
				String nullCobSession = null;
				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("cobSession", nullCobSession);
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).body(bodyParamAsString)
						.pathParams(eventMap).post(RestUtil.trimURL(url));
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession Session = new EnvSession(nullCobSession,
						configurationObj.getCobrandSessionObj().getPath());
				response =cobrandUtils.createNotificationEvent(httpParams, Session);
				break;

			} else if (key.equalsIgnoreCase("invalidCobKey")) {

				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("invalidCobKey", envSession.getCobSession());
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).body(bodyParamAsString)
						.pathParams(eventMap).post(RestUtil.trimURL(url));
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession Session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				response =cobrandUtils.createNotificationEvent(httpParams, Session);
				break;
			} else if (key.equalsIgnoreCase("nullCobKey")) {

				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("", envSession.getCobSession());
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).body(bodyParamAsString)
						.pathParams(eventMap).post(RestUtil.trimURL(url));
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession Session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				response =cobrandUtils.createNotificationEvent(httpParams, Session);
				break;
			} else if (key.equalsIgnoreCase("invalidEndpointUrl")) {
				response = RestUtil.postWithPathParamAndBodyAndCobSession(bodyParamAsString, eventMap, url + "/asd",
						envSession.getCobSession(), "application/json");
				break;
			}

		}

		case "UPDATE": {
			if (key.equalsIgnoreCase("special")) {
				
			//	ApiUtils.deleteSubscription("REFRESH",InitialLoader.cobSessionObj);
				EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters pathParam= cobrandHelper.createPathParam("eventName", "REFRESH");
				cobrandUtils.deleteNotificationEvent(pathParam, session);
				response = RestUtil.putRequestWithBodyParamAndPathParam(bodyParamAsString, eventMap, url,
						envSession.getCobSession(), "application/json");
				HttpMethodParameters httpParams=cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event, bodyParamAsString);
				response = cobrandUtils.putNotificationEvent(httpParams, session);
				break;
			} else if (key.equalsIgnoreCase("valid")) { // POSITIVE - CORRECTION REQD
				// TC#19 => First subscribe to an event and then trying to update it with valid
				// params.
				response = RestUtil.putRequestWithBodyParamAndPathParam(bodyParamAsString, eventMap, url,
						envSession.getCobSession(), "application/json");
				EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters httpParams=cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event, bodyParamAsString);
				response = cobrandUtils.putNotificationEvent(httpParams, session);
				
				break;
			} else if (key.equalsIgnoreCase("invalidCobSession")) {

				String invalidCobSession = configurationObj.getCobrandSessionObj().getCobSession() + "azs99";
				response = RestUtil.putRequestWithBodyParamAndPathParam(bodyParamAsString, eventMap, url,
						invalidCobSession, "application/json");
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession session = new EnvSession(invalidCobSession,
						configurationObj.getCobrandSessionObj().getPath());
				response = cobrandUtils.putNotificationEvent(httpParams, session);
				break;
			} else if (key.equalsIgnoreCase("nullCobSession")) {

				String nullCobSession = null;
				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("cobSession", nullCobSession);
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).body(bodyParamAsString)
						.pathParams(eventMap).put(RestUtil.trimURL(url));
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				response = cobrandUtils.putNotificationEvent(httpParams, session);
				break;

			} else if (key.equalsIgnoreCase("invalidCobKey")) {

				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("invalidCobKey", configurationObj.getCobrandSessionObj().getCobSession());
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).body(bodyParamAsString)
						.pathParams(eventMap).put(RestUtil.trimURL(url));
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				response = cobrandUtils.putNotificationEvent(httpParams, session);
				break;

			} else if (key.equalsIgnoreCase("nullCobKey")) {

				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("", configurationObj.getCobrandSessionObj().getCobSession());
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).body(bodyParamAsString)
						.pathParams(eventMap).put(RestUtil.trimURL(url));
				HttpMethodParameters httpParams = cobrandHelper.createInputForCreateOrUpdateNotificationEvents(Event,
						bodyParamAsString);
				EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				response = cobrandUtils.putNotificationEvent(httpParams, session);
				break;
			} else if (key.equalsIgnoreCase("invalidEndpointUrl")) {

				response = RestUtil.putRequestWithBodyParamAndPathParam(bodyParamAsString, eventMap, url + "/asd",
						envSession.getCobSession(), "application/json");
				break;
			}
		}

		case "GET": {

			if (key.equalsIgnoreCase("deleteAndGet")) {
		//		ApiUtils.deleteSubscription(Event,InitialLoader.cobSessionObj);
				EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters pathParam= cobrandHelper.createPathParam("eventName", Event);
				cobrandUtils.deleteNotificationEvent(pathParam, session);
				response = RestUtil.getWithQueryParamsAndCobSession(eventMap, GETUrl, envSession.getCobSession(),
						"application/json");
				EnvSession sessionObj = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters httpMethodParameters = cobrandHelper.createInputForGetNotificationEvents("eventName", Event);
				response = cobrandUtils.getNotificationEvents(httpMethodParameters, sessionObj);
				break;
			} else if (key.equalsIgnoreCase("validSession")) {
				response = RestUtil.getWithQueryParamsAndCobSession(eventMap, GETUrl, envSession.getCobSession(),
						"application/json");
				EnvSession sessionObj = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters httpMethodParameters = cobrandHelper.createInputForGetNotificationEvents("eventName", Event);
				response = cobrandUtils.getNotificationEvents(httpMethodParameters, sessionObj);

				break;
			} else if (key.equalsIgnoreCase("invalidSession")) {
				String invalidCobSession = configurationObj.getCobrandSessionObj().getCobSession() + "azs99";
				EnvSession sessionObj = new EnvSession(invalidCobSession,
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters httpMethodParameters = cobrandHelper.createInputForGetNotificationEvents("eventName", Event);
				response = cobrandUtils.getNotificationEvents(httpMethodParameters, sessionObj);
				response = RestUtil.getWithQueryParamsAndCobSession(eventMap, GETUrl, invalidCobSession,
						"application/json");

				break;
			} else if (key.equalsIgnoreCase("nullCobSession")) { // HERE
				String nullCobSession = null;
				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("cobSession", nullCobSession);
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(GETUrl)).queryParams(eventMap)
						.get(RestUtil.trimURL(GETUrl));
				EnvSession sessionObj = new EnvSession(nullCobSession,
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters httpMethodParameters = cobrandHelper.createInputForGetNotificationEvents("eventName", Event);
				response = cobrandUtils.getNotificationEvents(httpMethodParameters, sessionObj);
				break;

			} else if (key.equalsIgnoreCase("invalidCobKey")) {

				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("invalidCobKey", configurationObj.getCobrandSessionObj().getCobSession());
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(GETUrl)).queryParams(eventMap)
						.get(RestUtil.trimURL(GETUrl));
				EnvSession sessionObj = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters httpMethodParameters = cobrandHelper.createInputForGetNotificationEvents("eventName", Event);
				response = cobrandUtils.getNotificationEvents(httpMethodParameters, sessionObj);

				break;
			} else if (key.equalsIgnoreCase("nullCobKey")) {

				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("",  configurationObj.getCobrandSessionObj().getCobSession());
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(GETUrl)).queryParams(eventMap)
						.get(RestUtil.trimURL(GETUrl));
				EnvSession sessionObj = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters httpMethodParameters = cobrandHelper.createInputForGetNotificationEvents("eventName", Event);
				response = cobrandUtils.getNotificationEvents(httpMethodParameters, sessionObj);

				break;
			} else if (key.equalsIgnoreCase("invalidEndpointUrl")) {
				String invalidEndPointURL = GETUrl.replace("events", "invalid");
				response = RestUtil.getWithQueryParamsAndCobSession(eventMap, invalidEndPointURL,
						envSession.getCobSession(), "application/json");
				EnvSession sessionObj = new EnvSession(invalidEndPointURL,
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters httpMethodParameters = cobrandHelper.createInputForGetNotificationEvents("eventName", Event);
				response = cobrandUtils.getNotificationEvents(httpMethodParameters, sessionObj);
				break;
			}
		}

		case "DELETE": {

			if (key.equalsIgnoreCase("deleteAndDelete")) {
			//	ApiUtils.deleteSubscription(Event,InitialLoader.cobSessionObj);
				EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
						configurationObj.getCobrandSessionObj().getPath());
				HttpMethodParameters pathParam= cobrandHelper.createPathParam("eventName", Event);
				cobrandUtils.deleteNotificationEvent(pathParam, session);
				response = RestUtil.deleteWithPathParamAndCobSession(eventMap, url, envSession.getCobSession(),
						"application/json");
				break;
			}
			if (key.equalsIgnoreCase("validSession")) {
				response = RestUtil.deleteWithPathParamAndCobSession(eventMap, url, envSession.getCobSession(),
						"application/json");
				break;
			}

			if (key.equalsIgnoreCase("invalidSession")) {
				String invalidCobSession = envSession.getCobSession() + "azs99";
				response = RestUtil.deleteWithPathParamAndCobSession(eventMap, url, invalidCobSession,
						"application/json");
				break;
			}
			if (key.equalsIgnoreCase("nullCobSession")) {
				String nullCobSession = null;

				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("cobSession", nullCobSession);
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).pathParams(eventMap)
						.delete(RestUtil.trimURL(url));
				break;

			}

			if (key.equalsIgnoreCase("invalidCobKey")) {
				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("invalidCobKey", envSession.getCobSession());
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).pathParams(eventMap)
						.delete(RestUtil.trimURL(url));
				break;
			}

			if (key.equalsIgnoreCase("nullCobKey")) {
				LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
				hparams.put("", envSession.getCobSession());
				response = RestAssured.given().contentType("application/json")
						.headers("Authorization", hparams.toString()).headers("Api-Version", "1.0")
						.headers("Cobrand-Name", RestUtil.getBrandName(url)).pathParams(eventMap)
						.delete(RestUtil.trimURL(url));
				break;
			}

			if (key.equalsIgnoreCase("invalidEndpointUrl")) {
				String invalidEndPointURL = url.replace("events", "invalid");
				response = RestUtil.deleteWithPathParamAndCobSession(eventMap, invalidEndPointURL,
						envSession.getCobSession(), "application/json");
				break;
			}
		}

		}
		return response;
	}*/	
	
	public HttpMethodParameters createQueryParamsForGetEvents(String fromDateAsString, String toDateAsString,
			String userName) {
		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
		queryParam.put("fromDate", fromDateAsString);
		queryParam.put("toDate", toDateAsString);
		queryParam.put("eventName", "DATA_UPDATES");

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		return httpParams;
		
	}
	
}
