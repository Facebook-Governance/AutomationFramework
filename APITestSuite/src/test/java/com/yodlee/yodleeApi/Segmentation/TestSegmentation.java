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

/**
 * Validate Segmentation by explicitily paasing segmentNameAttribute
 * Configurations required : API1.1 and SN1.0
 */
package com.yodlee.yodleeApi.Segmentation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.commonUtils.RestUtil;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.URLConstants;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SegmentationHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class TestSegmentation extends Base {

	public static String segmentName, userSession;
	public String aclValue, aclValue1, aclValue2;
	DbHelper dbHelper = new DbHelper();
	public String inputSegmentName, updateSegmentName, segmentId = "";
	Configuration config = Configuration.getInstance();
	CommonUtils commonUtils = new CommonUtils();
	RestUtil restUtil = new RestUtil();
	SegmentationHelper segmentHelper = new SegmentationHelper();

	@BeforeClass()
	public void setup() {
		// dbHelper.connectToDb();

		System.out.println(Configuration.getInstance().getCobrandObj().getSegmentVersion());		
		String query = "select acl_value from cobrand_acl_value where param_acl_id=7466 and cobrand_id="
				+ config.getCobrandObj().getCobrandId();
		String query1 = "select param_value from cob_param where param_key_id=6352 and cobrand_id="
				+ config.getCobrandObj().getCobrandId();
		String query2 = "select param_value from cob_param where param_key_id=6016 and cobrand_id="
				+ config.getCobrandObj().getCobrandId();
		try {
			aclValue = dbHelper.getValueFromDB(query);
			System.out.println("GENERAL.REFRESH.SDG.CONFIG : " + aclValue);
			aclValue1 = dbHelper.getValueFromDB(query1);
			System.out.println("COM.YODLEE.CORE.USER.SEGMENTATION.VERSION : " + aclValue1);
			aclValue2 = dbHelper.getValueFromDB(query2);
			System.out.println("COM.YODLEE.CORE.SEGMENTATION.DEFAULT_SEGMENT_ID : " + aclValue2);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		if ((aclValue.equalsIgnoreCase("ON")) && (aclValue1.equalsIgnoreCase("1.0"))) {
			AssertJUnit.assertTrue("Issue in configurations !!", true);
		}

	}

	/**
	 * Registering an user with segmentName Verify by calling GET User Details
	 * API Updating the user with same/different segmentName Verify by calling
	 * GET User Details API Verify DB entry in memSegemnentsHistroy table
	 * 
	 * @throws Exception
	 **/
	@Test(description = "Update valid segmentName", dataProvider = "feeder", enabled = true)
	@Source("\\TestData\\CSVFiles\\Segmentation\\segmentNamePositive1.0.csv")
	public void registerSegmentName(String testCaseId, String testCaseName, String seg, String updateSeg,
			String DBValidations, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("********************TestCaseName=" + testCaseName + "**************************");
		String query = "select * from segments where cobrand_id=" + config.getCobrandObj().getCobrandId()
				+ " and is_deleted=0";
		ArrayList<String> strarray = segmentHelper.getValue(query, "SEGMENTS_ID", "SEGMENTS_NAME");
		if (seg.equals("segName1")) {
			String str = strarray.get(1);
			inputSegmentName = str;
		} else {
			inputSegmentName = "";
		}
		logger.debug("Registering User");
		String loginName = "segUser" + System.currentTimeMillis();
		User userInfo = User.builder().address1("Yodlee").address2("Bang").city("Banglaore").zip("560079")
				.country("INDIA").currency("USD").timeZone("PST").dateFormat("dd/mm/yyyy").locale("en_US")
				.segment(inputSegmentName).username(loginName).password("TEST@123").build();
		UserHelper userHelper = new UserHelper();
		String json = userHelper.createJSONforUserRegisterObject(userInfo);
		UserUtils util = new UserUtils();

		Session session = config.getCobrandSessionObj();
		session.setPath(config.getCobrandSessionObj().getPath() + URLConstants.USER_REGISTER);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(json);

		Response response = (Response) util.userRegistrationResponse(session, httpParams);

		userSession = response.jsonPath().getString("user.session.userSession");
		long mem_id = response.jsonPath().getLong("user.id");
		System.out.println("userSession : " + userSession);
		int statusCode = response.statusCode();
		AssertJUnit.assertEquals("User Registration Failed!!", 200, statusCode);
		logger.debug("GET User Details API");
		String responseSegmentName = segmentHelper.getUserDetils(userSession);
		if ((inputSegmentName.equalsIgnoreCase("")) && (responseSegmentName == null) && (aclValue2.isEmpty())) {
			assertTrue(true);
		} else if ((inputSegmentName.equalsIgnoreCase("")) && (!aclValue2.isEmpty())) {// SMB
																						// Feature
																						// has
																						// been
																						// handled
			String query1 = "select segments_name from segments where segments_id=" + aclValue2 + " and cobrand_id="
					+ config.getCobrandObj().getCobrandId() + " and is_deleted=0";
			String SMBSegmentName = dbHelper.getValueFromDB(query1);
			AssertJUnit.assertEquals(SMBSegmentName, responseSegmentName);
		} else {
			AssertJUnit.assertEquals(inputSegmentName, responseSegmentName);
		}
		if (updateSeg.equals("segName1")) {
			String str = strarray.get(1);
			updateSegmentName = str;
		} else if (updateSeg.equals("segName2")) {
			String str = strarray.get(3);
			updateSegmentName = str;
		} else {
			updateSegmentName = "";
		}
		logger.debug("Update User Details API");
		userInfo.setSegment(updateSegmentName);
		String updatedJSON = userHelper.createJSONforUserRegisterObject(userInfo);
		httpParams.setBodyParams(updatedJSON);

		session.setPath(config.getCobrandSessionObj().getPath() + URLConstants.USER_GET_AND_UPDATE);
		Response updateResponse = util.updateUserDetails(httpParams, session);

		int updatestatusCode = updateResponse.statusCode();
		AssertJUnit.assertEquals("User Details sucessfully updated", 204, updatestatusCode);
		logger.debug("GET User Details API after Update");
		String updateresponseSegName = segmentHelper.getUserDetils(userSession);
		if ((updateSegmentName.equalsIgnoreCase("")) && (updateresponseSegName == null)) {
			assertTrue(true);
		} else {
			AssertJUnit.assertEquals(updateSegmentName, updateresponseSegName);
		}
		logger.debug("Verify DB entry in memSegemnentsHistroy table");
		if (DBValidations.equals("notNull")) {
			String segmentId = verifyMemSegHistroy(mem_id);
			String expectedSegmentId = strarray.get(0);
			AssertJUnit.assertEquals("segementId doesn't match!!", expectedSegmentId, segmentId);
		} else if (DBValidations.equals("Null") && (!aclValue2.isEmpty())) {
			String segmentId = verifyMemSegHistroy(mem_id);
			String expectedSegmentId = aclValue2;
			AssertJUnit.assertEquals("segementId doesn't match!!", expectedSegmentId, segmentId);
		} else if (DBValidations.equals("Null") || (DBValidations.equals("alwaysNull"))) {
			String segmentId = verifyMemSegHistroy(mem_id);
			AssertJUnit.assertEquals("segementId doesn't match!!", "", segmentId);
		}

		session.setPath(config.getCobrandSessionObj().getPath() + URLConstants.USER_UNREGISTER);
		Response deleteUser = util.unRegisterUser(session);

		AssertJUnit.assertEquals("Failed to Delete an USER!!", 204, deleteUser.statusCode());
	}

	/**
	 * Registering an user with different timeZone and local Verify by calling
	 * GET User Details API
	 **/
	@Test(enabled = true, dataProvider = "feeder", alwaysRun = true)
	@Source("\\TestData\\CSVFiles\\Segmentation\\SegmentationTimeAndLocale1.0.csv")
	public void teacaUserRegWithdifferentTimeZoneAndLocale(String TCId, String TCTitle, String password, String email,
			String firstName, String lastName, String address1, String address2, String state, String city, String zip,
			String country, String currency, String timeZone, String dateFormat, String locale, int status,
			String ResFile, String FilePath, String Enabled) throws JSONException, SQLException {
		commonUtils.isTCEnabled(Enabled, TCId);
		System.out.println("*****teacaUserRegistrationWithdifferentTimeZone Starts*****");
		String loginName = "Seg" + System.currentTimeMillis();
		String query = "select * from segments where cobrand_id=" + config.getCobrandObj().getCobrandId()
				+ " and is_deleted=0";
		ArrayList<String> strarray = segmentHelper.getValue(query, "SEGMENTS_ID", "SEGMENTS_NAME");
		String segmentNameFromDB = strarray.get(1);
		User userInfo = User.builder().address1("Yodlee").address2("Bang").city("Banglaore").zip("560079")
				.country("INDIA").currency("USD").timeZone("PST").dateFormat("dd/mm/yyyy").locale("en_US")
				.segment(inputSegmentName).build();
		UserHelper userHelper = new UserHelper();
		String json = userHelper.createJSONforUserRegisterObject(userInfo);
		UserUtils util = new UserUtils();

		Session session = config.getCobrandSessionObj();
		session.setPath(config.getCobrandSessionObj().getPath() + URLConstants.USER_REGISTER);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(json);

		Response response = (Response) util.userRegistrationResponse(session, httpParams);
		System.out.println("Response after the registering user" + response.prettyPrint());
		String newUserSession = response.jsonPath().getString("user.session.userSession");
		// Get the user and verify the time zone

		session.setPath(config.getCobrandSessionObj().getPath() + URLConstants.USER_GET_AND_UPDATE);
		session.setUserSession(newUserSession);
		Response getUserDetails = util.getUserDetails(session);
		// Verifying segment name
		Assert.assertEquals(getUserDetails.jsonPath().getString("user.segmentName"), segmentNameFromDB);
		// Assert Get User Details Response
		JsonAssertionUtil jsonAssert = new JsonAssertionUtil();
		jsonAssert.assertJSON(getUserDetails, status, ResFile, FilePath);
		session.setPath(session.getPath() + URLConstants.USER_UNREGISTER);
		Response deleteUser = util.unRegisterUser(session);
		AssertJUnit.assertEquals("Failed to Delete an USER!!", 204, deleteUser.statusCode());
	}

	/**
	 * User Registration with invalid segmentName
	 */
	@Test(description = "Update valid segmentName", dataProvider = "feeder", enabled = true)
	@Source("\\TestData\\CSVFiles\\Segmentation\\regSegmentNegative1.0.csv")
	public void regSegNameNegative(String testCaseId, String testCaseName, String segmentNameKey,
			String segmentNameValue, String responseFile, int status, String filePath, String enabled)
			throws SQLException {
		commonUtils.isTCEnabled(enabled, testCaseId);
		String loginName = "SegmentUser" + System.currentTimeMillis();
		SegmentationHelper segmentHelper = new SegmentationHelper();
		String json = segmentHelper.registerSegmentName(loginName, segmentNameKey, segmentNameValue);

		Session session = config.getCobrandSessionObj();
		session.setPath(config.getCobrandSessionObj().getPath() + URLConstants.USER_REGISTER);

		HttpMethodParameters params = HttpMethodParameters.builder().build();
		params.setBodyParams(json);
		UserUtils util = new UserUtils();

		Response response = (Response) util.userRegistrationResponse(session, params);
		response.then().log().all();
		int statusCode = response.statusCode();
		assertEquals(statusCode, status);
		if (status == 400) {
			JsonAssertionUtil jsonAssert = new JsonAssertionUtil();
			jsonAssert.assertJSON(response, responseFile, filePath);
		}
	}

	/**
	 * User Registration without segmentName, and update with invalid
	 * segmentName
	 */
	@Test(description = "Update invalid segmentName", dataProvider = "feeder", enabled = true)
	@Source("\\TestData\\CSVFiles\\Segmentation\\updateSegmentNegative1.0.csv")
	public void updateSegNameNegative(String testCaseId, String testCaseName, String segmentNameKey,
			String segmentNameValue, String responseFile, String filePath, int status, String enabled)
			throws SQLException {
		commonUtils.isTCEnabled(enabled, testCaseId);
		String loginName = "SegmentUser" + System.currentTimeMillis();
		User userInfo = User.builder().address1("Yodlee").address2("Bang").city("Banglaore").zip("560079")
				.country("INDIA").currency("USD").timeZone("PST").dateFormat("dd/mm/yyyy").locale("en_US")
				.segment(inputSegmentName).build();
		UserHelper userHelper = new UserHelper();
		String json = userHelper.createJSONforUserRegisterObject(userInfo);
		UserUtils util = new UserUtils();

		Session session = config.getCobrandSessionObj();
		session.setPath(config.getCobrandSessionObj().getPath() + URLConstants.USER_REGISTER);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(json);

		Response response = (Response) util.userRegistrationResponse(session, httpParams);
		String userSession = response.jsonPath().getString("user.session.userSession");
		System.out.println("userSession : " + userSession);
		SegmentationHelper segmentHelper = new SegmentationHelper();
		String updatejson = segmentHelper.updateSegmentAttribute(segmentNameKey, segmentNameValue);

		session.setPath(config.getCobrandSessionObj().getPath() + URLConstants.USER_GET_AND_UPDATE);
		HttpMethodParameters params = HttpMethodParameters.builder().build();
		params.setBodyParams(updatejson);

		Response updateResponse = util.updateUserDetails(params, session);

		updateResponse.then().log().all();
		int statusCode = updateResponse.statusCode();
		AssertJUnit.assertEquals(status, statusCode);
		JsonAssertionUtil jsonAssert = new JsonAssertionUtil();
		jsonAssert.assertJSON(updateResponse, responseFile, filePath);

	}

	public String verifyMemSegHistroy(long mem_id) {

		String query = "select prev_mem_segments_id from mem_segments_history where mem_id=" + mem_id
				+ " and cobrand_id=" + config.getCobrandObj().getCobrandId() + " order by row_created desc";

		try {
			segmentId = dbHelper.getValueFromDB(query);
			System.out.println("Previous mem SegmentId is : " + segmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return segmentId;
	}

}
