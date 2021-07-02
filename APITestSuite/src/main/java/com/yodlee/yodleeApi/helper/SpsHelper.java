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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author jagannathanvs
 * This class is used in creating Json requests for SPS related testing
 *
 */
public class SpsHelper {

	
	/**
	 * This method is used to create Json requests for SPS admin config API
	 * 
	 * @param configKey
	 * @param configValue
	 * @param cobrandId
	 * @return
	 */
	public String createJsonRequestForSPSConfig(String configKey,
			String configValue, String cobrandId) {
		JSONObject jsonObj = new JSONObject();

		try {
			jsonObj.put("configKey", configKey);
			jsonObj.put("configValue", configValue);
			jsonObj.put("cobrandId", cobrandId);
			String json = jsonObj.toString();

			return json;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
