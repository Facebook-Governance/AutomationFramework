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
package com.yodlee.restApi.helper;

import java.util.HashMap;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;


import io.restassured.response.Response;

public class AccountAdditionHelper {

	protected Logger logger = LoggerFactory.getLogger(AccountAdditionHelper.class);

	public HttpMethodParameters AddSite(String siteId, String dagUserName, String dagPwd, String usersSessionToken,
			String coBrandSessionToken, Response SiteLoginformResponse) throws Exception {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cobSessionToken", coBrandSessionToken);
		params.put("userSessionToken", usersSessionToken);
		params.put("siteId", siteId);
		params.put("credentialFields.enclosedType", "com.yodlee.common.FieldInfoSingle");
		String JSONresponse = SiteLoginformResponse.asString();
		JSONObject JSONObj = new JSONObject(JSONresponse);
		JSONArray provider = JSONObj.getJSONArray("componentList"); // parsing
																	// the login
																	// form

		for (int i = 0; i < provider.length(); i++) {
			JSONObject objects = provider.getJSONObject(i);
			String[] elementNames = JSONObject.getNames(objects);
			System.out.printf("%d ELEMENTS IN CURRENT OBJECT:\n", elementNames.length);
			for (String elementName : elementNames) {
				if (elementName.equalsIgnoreCase("fieldType")) {
					params.put("credentialFields[" + i + "].fieldType.typeName",
							SiteLoginformResponse.jsonPath().getString("componentList[" + i + "].fieldType.typeName"));
					continue;
				}
				Object value = objects.get(elementName);
				params.put("credentialFields[" + i + "]." + elementName, value.toString());
				System.out.printf("name=%s, value=%s\n", elementName, value);
			}
			System.out.println();
			if (objects.getString("valueIdentifier").equalsIgnoreCase("LOGIN")) {
				params.put("credentialFields[" + i + "].value", dagUserName);
			} else if (objects.getString("valueIdentifier").equalsIgnoreCase("PASSWORD")) {
				params.put("credentialFields[" + i + "].value", dagPwd);
			}
		}

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setFormParams(params);

		return httpParams;

	}

	public HttpMethodParameters GetSiteLoginForm(String siteId, String coBrandSessionToken) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("siteId", siteId);
		params.put("cobSessionToken", coBrandSessionToken);
		

		HttpMethodParameters methodParams = HttpMethodParameters.builder().build();
		methodParams.setFormParams(params);
		return methodParams;
	}

}
