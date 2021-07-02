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

package com.yodlee.yodleeApi.Teaca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author partha sham
 *
 */
public class TestTeacaProviders extends Base {

	CommonUtils commonUtils = new CommonUtils();
	ProviderUtils providerUtils = new ProviderUtils();
	Configuration configuration = Configuration.getInstance();
	
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersBarclay.csv")
	public void teacaGetProvidersBarclay(String testCaseID,String testCaseName,String bugID,String enabled) {
		
		commonUtils.isTCEnabled(enabled, testCaseID);
		int siteId = 4081;
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", siteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("The given site id and its site name is" + siteId);
		JSONObject res = new JSONObject(response.prettyPrint());
		org.json.JSONArray getProvider = res.getJSONArray("provider");
		JSONObject json = getProvider.getJSONObject(0);
		String container = json.getString("name");
		System.out.println("The given site id and its site name is " + container);
		Assert.assertEquals("Barclaycard", container);
	}

	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersBetaStatus.csv")
	public void teacaGetProvidersBetaStatus(String testCaseID,String testCaseName,String bugID,String enabled) {
		
		commonUtils.isTCEnabled(enabled, testCaseID);
		Long siteId = 12222L;
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", siteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("The given site id and its site name is" + siteId);
		if (response.prettyPrint().isEmpty()) {
			System.out.println("SITE is not enabled so it is going to fail");
			Assert.assertEquals("abc", "bc");
		} else {
			JSONObject res = new JSONObject(response.prettyPrint());
			System.out.println("Please verify SITE is enabled or not " + siteId);
			org.json.JSONArray getTrans = res.getJSONArray("provider");
			JSONObject json = getTrans.getJSONObject(0);

			String container = json.getString("status");
			Long id = json.getLong("id");
			System.out.println("The given site id and its site name is " + container);
			Assert.assertEquals("Beta", container);
			System.out.println("The given site id and its site name is " + id);
			Assert.assertEquals(siteId, id);
		}
		siteId = 18738L;

		mapPath.put("providerId", siteId);
		httpParam.setPathParams(mapPath);
		response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("The given site id and its site name is" + siteId);
		if (response.prettyPrint().isEmpty()) {
			System.out.println("SITE is not enabled so it is going to fail" + siteId);
			Assert.assertEquals("abc", "bc");
		} else {
			JSONObject res = new JSONObject(response.prettyPrint());
			System.out.println("Please verify SITE is enabled or not " + siteId);
			org.json.JSONArray getProvider = res.getJSONArray("provider");
			JSONObject json = getProvider.getJSONObject(0);

			String container = json.getString("status");
			Long id = json.getLong("id");
			System.out.println("The given site id and its site name is " + container);
			Assert.assertEquals("Beta", container);
			System.out.println("The given site id and its site name is " + id);
			Assert.assertEquals(siteId, id);
		}
	}

	/*
	 * 
	 * 829958 - Incorrect number of transaction days for bank of america
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersBankOfAmerica.csv")
	public void teacaGetProvidersBankOfAmerica(String testCaseID,String testCaseName,String bugID,String enabled) {
		
		commonUtils.isTCEnabled(enabled, testCaseID);
		Long siteId = 2852L;
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", siteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("The given site id and its site name is" + siteId);
		if (response.prettyPrint().isEmpty()) {
			System.out.println("SITE is not enabled so it is going to fail");
			Assert.assertEquals("abc", "bc");
		} else {
			JSONObject res = new JSONObject(response.prettyPrint());
			System.out.println("Please verify SITE is enabled or not " + siteId);
			JSONArray getProvider = res.getJSONArray("provider");
			JSONObject json = getProvider.getJSONObject(0);
			JSONObject json1 = json.getJSONObject("containerAttributes");
			JSONObject json2 = json1.getJSONObject("BANK");
			Long numberOfDays = json2.getLong("numberOfTransactionDays");
			System.out.println("THE VALUE" + json.getJSONObject("containerAttributes").toString());
			System.out.println("THE VALUE" + json1.getJSONObject("BANK").toString());
			System.out.println("THE VALUE is " + json2.getLong("numberOfTransactionDays"));
			Long hardvalue = 365L;
			Long id = json.getLong("id");
			System.out.println("The given site id and its site name is " + numberOfDays);
			Assert.assertEquals(hardvalue, numberOfDays);
			System.out.println("The given site id and its site name is " + id);
			Assert.assertEquals(siteId, id);
		}
	}
	
	/*
	 * 882936 - wolterskluweruk , HSBC net is listed for our environment as Beta
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersHSBC.csv")
	public void teacaGetProvidersHSBC(String testCaseID,String testCaseName,String bugID,String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		Long siteId = 21609L;
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", siteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("The given site id and its site name is" + siteId);
		System.out.println("Please verify SITE is enabled or not " + siteId);
		if (response.prettyPrint().isEmpty()) {
			System.out.println("SITE is not enabled so it is going to fail");
			Assert.assertEquals("abc", "bc");
		} else {
			JSONObject res = new JSONObject(response.prettyPrint());
			org.json.JSONArray getProvider = res.getJSONArray("provider");
		}
	}

	/*
	 * 905731 - Receiving incorrect error message "Service not supported"
	 * instead of "Invalid value for providerId"
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersInvalidProviderId.csv")
	public void teacaGetProvidersInvalidProviderId(String testCaseID,String testCaseName,String bugID,String enabled) {
		
		commonUtils.isTCEnabled(enabled, testCaseID);
		String strSiteId = "2852L";
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", strSiteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("INVALID " + response.prettyPrint());
		System.out.println("Please verify SITE is enabled or not " + strSiteId);
		System.out.println("ERROR MESSSAGE" + response.jsonPath().getString("errorMessage"));
		Assert.assertEquals("Invalid input", response.jsonPath().getString("errorMessage"));

	}

	/*
	 * 839506 - STAGE || Moven || Container Name different from attributes
	 */
	@Test(enabled = true,dataProvider = Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersContainerNames.csv")
	public void teacaGetProvidersContainerNames(String testCaseID,String testCaseName,String bugID,String enabled) {
		
		commonUtils.isTCEnabled(enabled, testCaseID);
		Long siteId = 15569L;
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", siteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("Metro Bank Retail " + response.prettyPrint());
		JSONObject res = new JSONObject(response.prettyPrint());
		System.out.println("Please verify SITE is enabled or not " + siteId);
		org.json.JSONArray getProvider = res.getJSONArray("provider");
		JSONObject json = getProvider.getJSONObject(0);
		JSONObject containerAttributes = json.getJSONObject("containerAttributes");
		JSONArray containerNames = json.getJSONArray("containerNames");
		System.out.println("THE VALUE" + json.getJSONObject("containerAttributes").toString());
		System.out.println("THE VALUE" + json.getJSONArray("containerNames").toString());
	}

	/*
	 * 803481 [SPR-13] In YSL, we are getting custom fields for login forms of
	 * Natwest and Barclays
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersCustomFeildsBarclaysAndNatwest.csv")
	public void teacaGetProvidersCustomFeildsBarclaysAndNatwest(String testCaseID,String testCaseName,String bugID,String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		Map<String, String> mapQuery = new HashMap<>();
		mapQuery.put("name", "Barclays");
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(mapQuery);
		Response response = providerUtils.getProviders(httpParam, configuration.getCobrandSessionObj());
		System.out.println("Barclays login form  " + response.prettyPrint());
		String res = response.prettyPrint();
		System.out.println("Please verify Barclays SITE is enabled or not ");
		boolean contains = res.contains("custom:");
		System.out.println("The VALUE of condition " + contains);
		boolean TRUE = false;
		if (contains)
			Assert.assertEquals(contains, TRUE);
		mapQuery.put("name", "Natwest");
		httpParam.setQueryParams(mapQuery);
		response = providerUtils.getProviders(httpParam, configuration.getCobrandSessionObj());
		System.out.println("Natwest " + response.prettyPrint());
		res = response.prettyPrint();
		System.out.println("Please verify Natwest SITE is enabled or not ");
		contains = res.contains("custom");
		System.out.println("The VALUE of condition " + contains);
		if (contains) {
			Assert.assertEquals(contains, TRUE);
		}
	}

	/*
	 * 839940
	 * 
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersLabelHSBC.csv")
	public void teacaGetProvidersLabelHSBC(String testCaseID,String testCaseName,String bugID,String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		Long siteId = 3968L;
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", siteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("Barclays login form  " + response.prettyPrint());
		System.out.println("Please verify SITE is enabled or not " + siteId);
		JSONObject res = new JSONObject(response.prettyPrint());
		org.json.JSONArray getProvider = res.getJSONArray("provider");
		JSONObject json = getProvider.getJSONObject(0);
		JSONObject json1 = json.getJSONObject("loginForm");
		org.json.JSONArray getArray = json1.getJSONArray("row");

		List<String> labelList = new ArrayList<String>();
		for (int i = 0; i < getArray.length(); i++) {
			JSONObject label1 = getArray.getJSONObject(i);
			String labelValue = label1.getString("label");
			labelList.add(labelValue);
		}
		System.out.println("The label value is " + labelList);
		Assert.assertTrue(labelList.contains("Your Username"));
		Assert.assertTrue(labelList.contains("Password"));
	}

	/*
	 * 858325 - logo field is not coming in the response providers API
	 * 
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaProvidersLogoVerification.csv")
	public void teacaProvidersLogoVerification(String testCaseID,String testCaseName,String bugID,String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		int siteId = 16441;
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", siteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("The given site id and its site name is" + siteId);
		JSONObject res = new JSONObject(response.prettyPrint());
		org.json.JSONArray getProvider = res.getJSONArray("provider");
		JSONObject json = getProvider.getJSONObject(0);

		String logo = json.getString("logo");
		String favicon = json.getString("favicon");
		System.out.println("The given site id and its Logo name is " + siteId + " " + logo);
		Assert.assertEquals("https://yodlee-1.hs.llnwd.net/v1/LOGO/LOGO_16441_1_1.PNG", logo);
	}

	/*
	 * 881948- In Production, Getting same container multiple times under
	 * additionalDataSet attribute.
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaProvidersDuplicateContainerName.csv")
	public void teacaProvidersDuplicateContainerName(String testCaseID,String testCaseName,String bugID,String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		Long siteId = 4118L;
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerId", siteId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
		System.out.println("Metro Bank Retail " + response.prettyPrint());
		JSONObject res = new JSONObject(response.prettyPrint());
		System.out.println("Please verify SITE is enabled or not " + siteId);
		org.json.JSONArray getProvider = res.getJSONArray("provider");
		JSONObject json = getProvider.getJSONObject(0);
		JSONObject containerAttributes = json.getJSONObject("containerAttributes");
		JSONArray containerNames = json.getJSONArray("containerNames");
		System.out.println("THE VALUE" + json.getJSONObject("containerAttributes").toString());
		System.out.println("THE VALUE" + json.getJSONArray("containerNames").toString());
		String values = json.getJSONArray("containerNames").toString();
		System.out.println("The values json array to string" + values);
		ArrayList<String> listdata = new ArrayList<String>();
		int count = 0;
		if (containerNames != null) {
			for (int i = 0; i < containerNames.length(); i++) {
				listdata.add(containerNames.getString(i));
				if (listdata.get(i).equals("bank")) {
					count++;
				}
			}
		}
		System.out.println("Array List " + listdata);
		System.out.println("Bank occurs " + count);
		if (count == 1)
			Assert.assertEquals("bank", "bank");
		else
			Assert.assertEquals("bank", "banku");
		JSONArray additionalDataSet = json.getJSONArray("additionalDataSet");
		System.out.println("DATASET " + additionalDataSet.toString());
		JSONObject json1 = additionalDataSet.getJSONObject(0);
		JSONArray attributes = json1.getJSONArray("attribute");
		JSONArray contianerValue = attributes.getJSONObject(0).getJSONArray("container");
		System.out.println("FINAL values are" + contianerValue.toString());
		listdata = new ArrayList<String>();
		count = 0;
		if (contianerValue != null) {
			for (int i = 0; i < contianerValue.length(); i++) {
				listdata.add(contianerValue.getString(i));
				if (listdata.get(i).equals("bank")) {
					count++;
				}
			}
		}
		System.out.println("Array List " + listdata);
		System.out.println("Bank occurs " + count);
		if (count == 1)
			Assert.assertEquals("bank", "bank");
		else
			Assert.assertEquals("bank", "banku");
	}

	/*
	 * GET providers -count should be 500
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersCount.csv")
	public void teacaGetProvidersCount(String testCaseID,String testCaseName,String bugID,String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		Response response = providerUtils.getProviders(null, configuration.getCobrandSessionObj());
		System.out.println("The response of GET Providers " + response.prettyPrint());
		JSONObject res = new JSONObject(response.prettyPrint());
		org.json.JSONArray getProvider = res.getJSONArray("provider");
		List<String> priority = new ArrayList<String>();
		for (int i = 0; i <= getProvider.length() - 1; i++) {
			JSONObject json = getProvider.getJSONObject(i);
			priority.add(json.getString("PRIORITY"));
		}
		System.out.println("Total number of sites after calling GET providers " + priority.size());
		System.out.println("Verifying 500 count from GET Providers API ");
		Assert.assertEquals(500, priority.size());
	}

	/*
	 * skip and top different values to verify GET Providers API
	 * 
	 */
	@Test(enabled = true, dataProvider=Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersSkipAndTop.csv")
	public void teacaGetProvidersSkipAndTop(String testCaseID,String testCaseName,String filter, String value,String bugID,String enabled) {
		
		commonUtils.isTCEnabled(enabled, testCaseID);
		System.out.println("teacaGetProvidersSkipAndTop ::TESTING FOR filter  "+filter+" and value "+ value);
		org.json.JSONArray getProvider = null;
		Map<String, String> params = new HashMap<String, String>();
		List<String> priority = new ArrayList<String>();
		params.put(filter, value);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(params);
		int value1 = Integer.parseInt(value);
		Response response = providerUtils.getProviders(httpParam, configuration.getCobrandSessionObj());
		System.out.println("The response after invalid value of skip" + response.prettyPrint());
		JSONObject res = new JSONObject(response.prettyPrint());
		
		if (filter.equals("top") && Integer.parseInt(value) < 500) {
			getProvider = res.getJSONArray("provider");
			for (int i = 0; i <= getProvider.length() - 1; i++) {
				JSONObject json = getProvider.getJSONObject(i);
				priority.add(json.getString("PRIORITY"));
			}
			System.out.println("Total number of sites after calling GET providers " + priority.size());
			System.out.println("Verifying 500 count from GET Providers API ");
		}
		if (filter.equals("skip") ) {
			getProvider = res.getJSONArray("provider");
			for (int i = 0; i <= getProvider.length() - 1; i++) {
				JSONObject json = getProvider.getJSONObject(i);
				priority.add(json.getString("PRIORITY"));
			}
			System.out.println("Total number of sites after calling GET providers " + priority.size());
			System.out.println("Verifying 500 count from GET Providers API ");
		}
		if (filter.equals("top") && value1 < 500)
			Assert.assertEquals(value1, priority.size());
		else if (filter.equals("skip"))
			Assert.assertEquals(500, priority.size());
		if (filter.equals("top") && Integer.parseInt(value) > 500)
			Assert.assertEquals("Permitted values of top between 1 - 500",response.jsonPath().getString("errorMessage"));
		
		System.out.println("teacaGetProvidersSkipAndTop::TESTING FOR filter  "+filter+" and value "+ value + " completed");

	}
}
