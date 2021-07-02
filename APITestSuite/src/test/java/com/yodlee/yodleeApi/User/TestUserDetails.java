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

package com.yodlee.yodleeApi.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.ConfigKeysConstants;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import net.javacrumbs.jsonunit.core.Option;
import static net.javacrumbs.jsonunit.JsonAssert.when;
import io.restassured.response.Response;

/**
 * @author bhuvaneshwari
 *
 */
public class TestUserDetails extends Base {

	CommonUtils commonUtils = new CommonUtils();
	UserUtils userUtils = new UserUtils();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonPath = new JsonAssertionUtil();
	Configuration configurationObj = Configuration.getInstance();

	@SuppressWarnings("deprecation")
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\User\\testUserDetail.csv")
	public void testUserDetails(String TCId, String TCTitle, String password, String email, String firstName,
			String lastName, String address1, String address2, String state, String city, String zip, String country,
			String currency, String timeZone, String dateFormat, int status, String ResFile, String FilePath,
			String Enabled) throws JSONException, InterruptedException, JoseException, FileNotFoundException {
		commonUtils.isTCEnabled(Enabled, TCId);
		
		System.out.println("TCId"+ TCId +"TCTitle" +TCTitle);

		String jsonBody = userHelper.createUserRegistrationObject(password, email, firstName, lastName, address1,
				address2, state, city, zip, country, currency, timeZone, dateFormat);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonBody);
											
		String newUserSession = userUtils.userRegistration(configurationObj.getCobrandSessionObj(), httpParams);

		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.userSession(newUserSession).path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response getUserDetails = userUtils.getUserDetails(session);

		String date = configurationObj.getCobrandObj().getDateFormat();
						
		System.out.println("date::"+date);
		
//		String segmentName = configurationObj.getCobrandObj().getSegmentList().get(0);
				
		JSONObject jsonObj = readJsonFile(commonUtils.getPath(FilePath + ResFile));		
																												
		JSONObject jsonObj1 = jsonObj.getJSONObject("user");
		JSONObject jsonObj2 = jsonObj1.getJSONObject("preferences");
		jsonObj2.remove("dateFormat");
		jsonObj2.put("dateFormat", date);
	//	jsonObj1.put("segmentName", segmentName);
				
		System.out.println("jsonObj::"+jsonObj);		
		
		assertJsonEquals(jsonObj, getUserDetails.asString(), when(Option.IGNORING_ARRAY_ORDER));
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\User\\testForGetDetail.csv")
	public void testForGetUser(String testCaseId, String testCase, String userName, String password, int status,
			String enabled, String roletype) {

		String loginName = userName + System.currentTimeMillis();
		String json = userHelper.userRegistrationObject(loginName, password);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(json);

		EnvSession session = EnvSession.builder().build();
		session.setCobSession(configurationObj.getCobrandSessionObj().getCobSession());
		session.setPath(configurationObj.getCobrandSessionObj().getPath());

		Response response = (Response) userUtils.userRegistrationResponse(session, httpParams);
		Assert.assertEquals(200, response.statusCode());
		System.out.println("Trying for userSession from response");
		String newUserSession = response.jsonPath().getString("user.session.userSession");
		System.out.println("Trying for roleType from response");
		Assert.assertEquals("INDIVIDUAL", response.jsonPath().getString("user.roleType"));

		session.setUserSession(newUserSession);

		response = userUtils.getUserDetails(session);

		Assert.assertEquals(200, response.getStatusCode());
		System.out.println("Trying for loginName from response");
		Assert.assertEquals(loginName, response.jsonPath().getString("user.loginName"));
		System.out.println("Trying for roleType from response");
		Assert.assertEquals("INDIVIDUAL", response.jsonPath().getString("user.roleType"));

	}
	
	public String getPath(String filePath) {
		if (filePath.contains(System.getProperty("user.dir"))) {
			return filePath;
		}
		String workingDir = System.getProperty("user.dir");
		
		if(filePath.contains("..")) {
		filePath = filePath.substring(2);
		}
		
		if (filePath.contains(".json"))
            filePath = workingDir + filePath;
		else
            filePath = workingDir + filePath + ".json";
		
		return filePath;
	}
	
	public JSONObject readJsonFile(String filePath) throws FileNotFoundException {
		String sCurrentLine;
		String jsonFile = "";
		try (BufferedReader br = new BufferedReader(new FileReader(getPath(filePath)))) {
			while ((sCurrentLine = br.readLine()) != null) {
				jsonFile += sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject(jsonFile);
		return jsonObject;
	}

}
