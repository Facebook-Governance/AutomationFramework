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

import org.json.JSONException;
import org.json.JSONObject;

import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.Cobrand;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;

/**
 * @author Soujanya Kokala
 *
 */
public class CobrandHelper {

	/**
	 * This method is used to create JSON for cobrand login.
	 * 
	 * @param cobrandObj
	 * @return
	 */
	public String createCobrandLoginJson(Cobrand cobrandObj) {

		JSONObject cobrandLoginJson = new JSONObject();

		JSONObject cobrandObject = new JSONObject();
		cobrandObject.put(cobrandObj.getCobrandLoginKey(), cobrandObj.getCobrandLogin());
		cobrandObject.put(cobrandObj.getCobrandPasswordKey(), cobrandObj.getCobrandPassword());
		cobrandObject.put(cobrandObj.getLocaleKey(), cobrandObj.getLocale());

		cobrandLoginJson.put("cobrand", cobrandObject);

		return cobrandLoginJson.toString();

	}

	/**
	 * This method is used to create input HttpParams for cobrand login
	 * 
	 * @author Soujanya Kokala
	 * @param cobrandObj
	 * @return
	 */
	public HttpMethodParameters createInputForCobrandLogin(Cobrand cobrandObj) {

		JSONObject cobrandLoginJson = new JSONObject();

		JSONObject cobrandObject = new JSONObject();
		cobrandObject.put(cobrandObj.getCobrandLoginKey(), cobrandObj.getCobrandLogin());
		cobrandObject.put(cobrandObj.getCobrandPasswordKey(), cobrandObj.getCobrandPassword());
		cobrandObject.put(cobrandObj.getLocaleKey(), cobrandObj.getLocale());

		cobrandLoginJson.put("cobrand", cobrandObject);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(cobrandLoginJson.toString());

		return httpMethodParameters;

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

	/**
	 * This method is used to create input for create notification events. Used by
	 * API: create(POST)/update(PUT) Notification events
	 * 
	 * @param eventName:
	 *            can be like REFRESH , DATA_UPDATES etc.
	 * @param callbackUrl
	 *            is where the notification will be posted should be provided to
	 *            this service
	 * @return
	 * @throws JSONException
	 */
	public HttpMethodParameters createInputForCreateOrUpdateNotificationEvents(String eventName, String callBackUrl)
			throws JSONException {

		JSONObject eventObj = new JSONObject();
		JSONObject eventSubscriptionObj = new JSONObject();
		eventObj.put("callbackUrl", callBackUrl);
		eventSubscriptionObj.put("event", eventObj);
		String eventObjJSON = eventSubscriptionObj.toString();
		System.out.println("eventObjJSON is : " + eventObjJSON);

		HttpMethodParameters httpParams = createPathParam("eventName", eventName);
		httpParams.setBodyParams(eventObjJSON);

		return httpParams;
	}

	/**
	 * This method is used to create input for create notification events. Used by
	 * API: GET /cobrand/config/notifications/events
	 * 
	 * @param key
	 *            key name.
	 * @param value
	 *            value for that key.
	 * @return
	 * @throws JSONException
	 */
	public HttpMethodParameters createInputForGetNotificationEvents(String key, String value) throws JSONException {

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put(key, value);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParams);

		return httpParams;
	}
	
	 public String getCobrandSession(String cobSession, Session session) {
	        String cobrandSession = cobSession;
	        if (cobrandSession.equalsIgnoreCase("correct"))
	            cobrandSession = session.getCobSession();
	        return cobrandSession;
	    }

}
