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

package com.yodlee.yodleeApi.DataExtracts;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.DataExtractsHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.DataExtractUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class DataExtractsPaginationNonSDGTest extends Base {

	String userName;
	Long providerAccountId = null;
	final String dagSiteUserName = "SMB_Automation.site16441.4";
	final String dagSitePassword = "site16441.4";
	final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	String fromDateAsString;
	String toDateAsString;
	public static Long providerAccountId1 = null;

	Configuration configurationObj = Configuration.getInstance();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	DataExtractsHelper dataExtractsHelper = new DataExtractsHelper();
	DataExtractUtils dataExtractUtils = new DataExtractUtils();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();

	@BeforeClass(alwaysRun = true)
	public void Setup() throws IOException {
		long providerId = 16441;
		userName = configurationObj.getUserObj().getUsername();
		System.out.println("userName:::::"+userName);
		fromDateAsString = new SimpleDateFormat(ISO_FORMAT).format(getUTCtime());
	
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "loginform",
				"SMB_Automation.site16441.4", "site16441.4", configurationObj.getCobrandSessionObj());

		providerAccountId1 = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId1);

		Assert.assertNotNull(providerAccountId1, "Provider account id is null after account addition");
		try {
			Thread.sleep(50 * 1000); 
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted in DataExtractsPaginationNonSDG setUp()...");
			e.printStackTrace();
		}
		toDateAsString = new SimpleDateFormat(ISO_FORMAT).format(getUTCtime());
	}

	@Test(priority = 0, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\DataExtracts\\pagination_NonSDG.csv")
	public void validateDataExtractsPaginationOnNonSDGAfterAccountAddition(String testCaseID, String testcaseName,
			String isEnabled) {

		commonUtils.isTCEnabled(isEnabled, testCaseID);
		
	
		HttpMethodParameters httpParameter = dataExtractsHelper.createQueryParamsForGetEvents(fromDateAsString, toDateAsString, userName);
		Response cobrandDataResponse=dataExtractUtils.getEvents(httpParameter, configurationObj.getCobrandSessionObj());
		
		
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

        
        System.out.println("userDataHref::"+userDataHref);
        
        HttpMethodParameters  httpParameterUserData=HttpMethodParameters.builder().build();
        String fromDate1=userDataHref.substring(userDataHref.indexOf("fromDate=")+9, userDataHref.indexOf("toDate")-1);
        String toDate1=userDataHref.substring(userDataHref.indexOf("toDate=")+7, userDataHref.indexOf("loginName")-1);;
        String loginName1=userDataHref.substring(userDataHref.indexOf("loginName=")+10, userDataHref.length()); 
        
        Map<String, Object> queryParamsGetUserData=new LinkedHashMap<>();
       
		queryParamsGetUserData.put("fromDate", fromDate1);
        
		queryParamsGetUserData.put("toDate", toDate1);
       
		queryParamsGetUserData.put("loginName", loginName1);
        httpParameterUserData.setQueryParams(queryParamsGetUserData);
       
		Response userDataResponse_Page1 = dataExtractUtils.getUserData(httpParameterUserData, configurationObj.getCobrandSessionObj());
		
		if (userDataResponse_Page1.asString().contains("errorMessage")) {
			throw new RuntimeException(
					"error message is seen in page1 response - " + userDataResponse_Page1.asString());
		}
		JSONObject userDataObject = new JSONObject(
				(Map<?, ?>) userDataResponse_Page1.body().jsonPath().get("userData[0]"));
		
		Assert.assertEquals(userDataObject.getJSONArray("transaction").length(), 500,
				"Actual Count = " + userDataObject.getJSONArray("transaction").length() + ". Response - "
						+ userDataResponse_Page1.asString());
		
		String linkHeaderValue1 = userDataResponse_Page1.header("Link").replace(": /", "");
		String[] links = linkHeaderValue1.split(",");
		Assert.assertTrue(links[0].contains(";rel=next"),
				";rel=next is NOT present in header link of 1st page's response - " + links[0]);
		Assert.assertEquals(links.length, 1); // There should be only rel=next
		String linkValue1 = links[0].split(";")[0]; // Removes ;rel=next from the string. Currently error is thrown when
													// this matrix parameter is passed. A clarification has been raised
													// for this.
		// 2nd page's response - 500 to 1000
		Assert.assertNotNull(linkValue1);
		String cobrandName = configurationObj.getCobrandObj().getCobrandName();
		Assert.assertTrue(linkValue1.startsWith("ysl/" + cobrandName + "/v1/"),
				"Link header for v1.0 has to begin with ysl/" + cobrandName + "v1/"
						+ "Response for page1 does not begin with that. Link header value - " + linkValue1);
		linkValue1 = linkValue1.replace("ysl/" + cobrandName + "/v1/", "");
		System.out.println("Hitting DE user data with header link - " + linkValue1);
		
		//dataExtracts/userData?fromDate=2019-04-12T11:59:45Z&loginName=YSL1555070376757&toDate=2019-04-12T12:02:13Z&select=transaction&skip=500&top=500
		
		LinkedHashMap<String, Object> queryParms = new LinkedHashMap<>();
		
		// dataExtracts/userData?fromDate=2019-04-12T12:27:06Z&loginName=YSL1555072018448&toDate=2019-04-12T12:29:24Z&select=transaction&skip=500&top=500
		//dataExtracts/userData?fromDate=2019-04-12T12:21:25Z&loginName=YSL1555071677211&toDate=2019-04-12T12:22:46Z&select=transaction&skip=500&top=500
		
		fromDate1=linkValue1.substring(linkValue1.indexOf("fromDate=")+9, linkValue1.indexOf("loginName")-1);
		loginName1=linkValue1.substring(linkValue1.indexOf("loginName=")+10, linkValue1.indexOf("toDate")-1);;
        toDate1=linkValue1.substring(linkValue1.indexOf("toDate=")+7, linkValue1.indexOf("select")-1); 
        String select1=linkValue1.substring(linkValue1.indexOf("select=")+7, linkValue1.indexOf("skip")-1); 
        String skip1=linkValue1.substring(linkValue1.indexOf("skip=")+5, linkValue1.indexOf("top")-1);
        String top1=linkValue1.substring(linkValue1.indexOf("top=")+4, linkValue1.length());
        
        
       
        System.out.println("loginName1:"+loginName1);
        System.out.println("toDate1:"+toDate1);
        System.out.println("select1:"+select1);
        System.out.println("skip1:"+skip1);
        System.out.println("top1:"+top1);
		
		
		queryParms.put("fromDate", fromDate1);	
		queryParms.put("loginName", loginName1);	
		queryParms.put("toDate", toDate1);	
		queryParms.put("select", select1);	
		queryParms.put("skip", skip1);
		queryParms.put("top", top1);
		
		HttpMethodParameters httpParam= HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParms);
			
		HttpMethodParameters httpParameters= dataExtractsHelper.createQueryParamsForGetUser(fromDateAsString, toDateAsString, userName);
		httpParameters.setBodyParams(linkValue1.trim());

		Response userDataResponse_Page2 = dataExtractUtils.getUserData(httpParam, configurationObj.getCobrandSessionObj());
		if (userDataResponse_Page2.asString().contains("errorMessage")) {
			throw new RuntimeException(
					"error message is seen in page2 response - " + userDataResponse_Page2.asString());
		}
		userDataObject = new JSONObject((Map<?, ?>) userDataResponse_Page2.body().jsonPath().get("userData[0]"));
		Assert.assertEquals(userDataObject.getJSONArray("transaction").length(), 500,
				"Actual Count = " + userDataObject.getJSONArray("transaction").length() + ". Response - "
						+ userDataResponse_Page2.asString());
		String linkHeaderValue2 = userDataResponse_Page2.header("Link").replace(": /", "");// Current a bug has been
																							// raised for : /. This will
																							// have to be removed after
																							// bug fix.
		links = linkHeaderValue2.split(",");
		Assert.assertEquals(links.length, 2); // links are with rel=previous and rel=next
		String linkValue2 = links[1].split(",")[0]; // Separates the link with rel=next

		// 3rd page's response - 1000 to 1500
		Assert.assertNotNull(linkValue2);
	
		linkValue2 = linkValue2.trim(); // To remove the space in beginning
		Assert.assertTrue(linkValue2.contains(";rel=next"),
				";rel=next is NOT present in header link of 2nd page's response - " + linkValue2);
		linkValue2 = linkValue2.split(";")[0]; // Removes ;rel=next from string// Removes ;rel=next from the string.
												// Currently error is thrown when this matrix parameter is passed. A
												// clarification has been raised for this.
		Assert.assertTrue(linkValue2.startsWith("/ysl/" + cobrandName + "/v1/"),
				"Link header for v1.0 has to begin with /ysl/<cobrandname>/v1/. "
						+ "Response for page2 does not begin with that. Link header value - " + linkValue2);
		linkValue2 = linkValue2.replace("/ysl/" + cobrandName + "/v1/", "");
		System.out.println("Hitting DE user data with header link - " + linkValue2);
		
//dataExtracts/userData?fromDate=2019-04-12T12:50:58Z&loginName=YSL1555073449951&toDate=2019-04-12T12:53:11Z&select=transaction&skip=1000&top=500		
		
		LinkedHashMap<String, Object> queryParm = new LinkedHashMap<>();
		
		fromDate1=linkValue2.substring(linkValue2.indexOf("fromDate=")+9, linkValue2.indexOf("loginName")-1);
		loginName1=linkValue2.substring(linkValue2.indexOf("loginName=")+10, linkValue2.indexOf("toDate")-1);;
        toDate1=linkValue2.substring(linkValue2.indexOf("toDate=")+7, linkValue2.indexOf("select")-1); 
         select1=linkValue2.substring(linkValue2.indexOf("select=")+7, linkValue2.indexOf("skip")-1); 
         skip1=linkValue2.substring(linkValue2.indexOf("skip=")+5, linkValue2.indexOf("top")-1);
         top1=linkValue2.substring(linkValue2.indexOf("top=")+4, linkValue2.length());

         System.out.println("loginName1:"+loginName1);
        System.out.println("toDate1:"+toDate1);
        System.out.println("select1:"+select1);
        System.out.println("skip1:"+skip1);
        System.out.println("top1:"+top1);
		
		
        queryParm.put("fromDate", fromDate1);	
        queryParm.put("loginName", loginName1);	
        queryParm.put("toDate", toDate1);	
        queryParm.put("select", select1);	
        queryParm.put("skip", skip1);
        queryParm.put("top", top1);
		
		
		HttpMethodParameters httpParame= HttpMethodParameters.builder().build();
		httpParame.setQueryParams(queryParm);

		Response userDataResponse_Page3 = dataExtractUtils.getUserData(httpParame, configurationObj.getCobrandSessionObj());
		userDataResponse_Page3.then().log().all();
		if (userDataResponse_Page3.asString().contains("errorMessage")) {
			throw new RuntimeException(
					"error message is seen in page3 response - " + userDataResponse_Page3.asString());
		}
		userDataObject = new JSONObject((Map<?, ?>) userDataResponse_Page3.body().jsonPath().get("userData[0]"));
		Assert.assertEquals(userDataObject.getJSONArray("transaction").length(), 500,
				"Actual Count = " + userDataObject.getJSONArray("transaction").length() + ". Response - "
						+ userDataResponse_Page3.asString());
		String linkHeaderValue3 = userDataResponse_Page3.header("Link").replace(": /", ""); 
		links = linkHeaderValue3.split(",");
		Assert.assertEquals(links.length, 2, "Incorrect number of links in header - " + links); 
		String linkValue3 = links[1].split(",")[0];
		linkValue3 = linkValue3.replaceAll("/%20/", ""); 
		// 3rd page's response - 1500 to 1998
		Assert.assertNotNull(linkValue3);
		
		linkValue3 = linkValue3.trim(); 
		Assert.assertTrue(linkValue3.contains(";rel=next"),
				";rel=next is NOT present in header link of 3rd page's response - " + linkValue3);
		linkValue3 = linkValue3.split(";")[0]; 
		Assert.assertTrue(linkValue3.startsWith("/ysl/" + cobrandName + "/v1/"),
				"Link header for v1.0 has to begin with /ysl/<cobrandname>/v1/. "
						+ "Response for page3 does not begin with that. Link header value - " + linkValue3);
		linkValue3 = linkValue3.replace("/ysl/" + cobrandName + "/v1/", "");
		System.out.println("Hitting DE user data with header link - " + linkValue3);
		
		 
		LinkedHashMap<String, Object> queryParams = new LinkedHashMap<>();
		//queryParams.put("linkValue3", linkValue3.trim());	
		
		
		fromDate1=linkValue3.substring(linkValue3.indexOf("fromDate=")+9, linkValue3.indexOf("loginName")-1);
		loginName1=linkValue3.substring(linkValue3.indexOf("loginName=")+10, linkValue3.indexOf("toDate")-1);;
        toDate1=linkValue3.substring(linkValue3.indexOf("toDate=")+7, linkValue3.indexOf("select")-1); 
         select1=linkValue3.substring(linkValue3.indexOf("select=")+7, linkValue3.indexOf("skip")-1); 
         skip1=linkValue3.substring(linkValue3.indexOf("skip=")+5, linkValue3.indexOf("top")-1);
         top1=linkValue3.substring(linkValue3.indexOf("top=")+4, linkValue3.length());

         System.out.println("loginName1:"+loginName1);
        System.out.println("toDate1:"+toDate1);
        System.out.println("select1:"+select1);
        System.out.println("skip1:"+skip1);
        System.out.println("top1:"+top1);
		
		
        queryParams.put("fromDate", fromDate1);	
        queryParams.put("loginName", loginName1);	
        queryParams.put("toDate", toDate1);	
        queryParams.put("select", select1);	
        queryParams.put("skip", skip1);
        queryParams.put("top", top1);
		
		
		 HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		 httpParams.setQueryParams(queryParams);

		Response userDataResponse_Page4 = dataExtractUtils.getUserData(httpParams, configurationObj.getCobrandSessionObj());
		userDataResponse_Page4.then().log().all();
		if (userDataResponse_Page4.asString().contains("errorMessage")) {
			throw new RuntimeException("error message is seen in page4 response - " + userDataResponse_Page4.asString()
					+ ". Link used : " + linkValue3);
		}
		userDataObject = new JSONObject((Map<?, ?>) userDataResponse_Page4.body().jsonPath().get("userData[0]"));
		Assert.assertEquals(userDataObject.getJSONArray("transaction").length(), 498,
				userDataResponse_Page4.asString());
		Headers headers = userDataResponse_Page4.getHeaders();
		
		for (Header header : headers) {
			Assert.assertNotEquals(header.getValue(), "Link", "Link header is present on the last page");
		}
		
		String linkValue4 = linkValue3.replace("&select=transaction", "");
		System.out.println("Hitting DE user data without select=transaction in header link - " + linkValue4);
		linkValue4 = linkValue4.split(";")[0]; 
		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
		//queryParam.put("linkValue4", linkValue4.trim());
		//dataExtracts/userData?fromDate=2019-04-12T13:01:36Z&loginName=YSL1555074088226&toDate=2019-04-12T13:04:05Z&skip=1500&top=500
		
		
		fromDate1=linkValue4.substring(linkValue4.indexOf("fromDate=")+9, linkValue4.indexOf("loginName")-1);
		loginName1=linkValue4.substring(linkValue4.indexOf("loginName=")+10, linkValue4.indexOf("toDate")-1);;
        toDate1=linkValue4.substring(linkValue4.indexOf("toDate=")+7, linkValue4.indexOf("skip")-1); 
         skip1=linkValue4.substring(linkValue4.indexOf("skip=")+5, linkValue4.indexOf("top")-1);
         top1=linkValue4.substring(linkValue4.indexOf("top=")+4, linkValue4.length());

         System.out.println("loginName1:"+loginName1);
        System.out.println("toDate1:"+toDate1);
        System.out.println("skip1:"+skip1);
        System.out.println("top1:"+top1);
		
		
        queryParam.put("fromDate", fromDate1);	
        queryParam.put("loginName", loginName1);	
        queryParam.put("toDate", toDate1);	
        queryParam.put("skip", skip1);
        queryParam.put("top", top1);
		
		
		
		
		HttpMethodParameters httpMethodParam = HttpMethodParameters.builder().build();
		httpMethodParam.setQueryParams(queryParam);
		Response errorResponse = dataExtractUtils.getUserData(httpMethodParam, configurationObj.getCobrandSessionObj());
		errorResponse.then().log().all();
		Assert.assertTrue(errorResponse.asString().contains("select required"),
				"error message 'select required' is NOT seen when select=transaction is not given. Response seen - "
						+ errorResponse.asString());
	}

	private Date getUTCtime() {
		Date utcTime = null;
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		// Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		// Time in GMT
		try {
			utcTime = dateFormatLocal.parse(dateFormatGmt.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return utcTime;
	}

}
