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

package com.yodlee.yodleeApi.Providers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.CobrandHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * Class that validates Get providers feature.
 *
 */

public class TestGetProviders extends Base {
	public static final String GET_PROVIDERS_CSV = "\\TestData\\CSVFiles\\Providers\\GETProviders\\getProviders.csv";
	public static final String GET_PROVIDER_DETAILS_CSV = "\\TestData\\CSVFiles\\Providers\\GETProviders\\getProviderDetails.csv";
	
	CommonUtils commonUtils = new CommonUtils();
	Configuration configurationObj= Configuration.getInstance();
	ProviderUtils providerUtils = new ProviderUtils();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	UserHelper userHelper = new UserHelper();
	CobrandHelper cobrandHelper = new CobrandHelper();

	@BeforeClass(alwaysRun = true)
	public void setUpTest() {

	}
	
	/**
	 * Test that validates GET Providers.
	 */
	@Test(dataProvider = "feeder", enabled = true, priority = 1)
	@Source(GET_PROVIDERS_CSV)
	public void testGetProviders(String tcid, String tcName, String status, String resFile, String resFilePath,
			String usersSession, String cobSession, String priority, String capability, String name,
			String additionalDataSet, String classification, String skip, String top, String defectId, String enabled) {
		commonUtils.isTCEnabled(enabled, tcid);
		System.out.println("tcid::"+tcid +"tcName"+ tcName);
		String userSession = userHelper.getUserSession(usersSession, configurationObj.getCobrandSessionObj());
		String cobrandSession = cobrandHelper.getCobrandSession(cobSession, configurationObj.getCobrandSessionObj());
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("priority", priority);
		queryParam.put("capability", capability);
		queryParam.put("name", name);
		queryParam.put("additionalDataSet", additionalDataSet);
		queryParam.put("classification", classification);
		queryParam.put("skip", skip);
		queryParam.put("top", top);
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		
		EnvSession envSession = EnvSession.builder().cobSession(cobrandSession).userSession(userSession)
				.path(Configuration.getInstance().getCobrandSessionObj().getPath()).build();
		
		Response response = providerUtils.getProviders(httpParams, envSession);
		jsonAssertionUtil.assertResponse(response, Integer.parseInt(status), resFile, resFilePath);

	}

	@Test(dataProvider = Constants.FEEDER)
	@Source(GET_PROVIDER_DETAILS_CSV)
	public void testGetProviderDetails(String tcid, String tcName, String status, String resFile, String resFilePath,
			String usersSession, String cobSession, String providerId, String defectId, String enabled) {
		commonUtils.isTCEnabled(enabled, tcid);
		String userSession = userHelper.getUserSession(usersSession, configurationObj.getCobrandSessionObj());
		String cobrandSession = cobrandHelper.getCobrandSession(cobSession, configurationObj.getCobrandSessionObj());
	
		LinkedHashMap<String, Object> pathParam= new LinkedHashMap<>();
		pathParam.put("providerId", providerId);
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		
		EnvSession envSession = EnvSession.builder().cobSession(cobrandSession).userSession(userSession)
				.path(Configuration.getInstance().getCobrandSessionObj().getPath()).build();;
		Response response = providerUtils.getProviderDetails(httpParams, envSession);
		
		System.out.println("response toString()::"+response.toString());
		jsonAssertionUtil.assertResponse(response, Integer.parseInt(status), resFile, resFilePath);

	}

}
