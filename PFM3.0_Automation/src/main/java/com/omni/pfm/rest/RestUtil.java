/**
 * Copyright (c) 2014 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.omni.pfm.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.yodlee.jmx.client.Client;

public class RestUtil {
	public static Properties properties = RegisterUser.getProperty();

	/*
	 * private static Properties props= new Properties(); static{ try {
	 * props.load(new FileInputStream(new File("config_file"))); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
	 */
	public static Response get(Map<String, String> params, String path) {
		Response response = RestAssured.given().log().all().queryParams(params).header("Accept", "application/json")
				.get(path);
		return response;
	}

	public static Response post(Map<String, String> params, String path) {
		RequestSpecification request = RestAssured.given().log().all().formParams(params);
		request.relaxedHTTPSValidation();
		Response response = request.post(path);
		return response;
	}

	public static String getCobrandSessionToken() {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cobrandLogin", properties.getProperty("coBrandLogin"));
		params.put("cobrandPassword", properties.getProperty("coBrandPassword"));

		Response response = RestUtil.post(params,
				"https://rest.autopfmc.corp.yodlee.com:3443/services/srest/ymc/v1.0/authenticate/coblogin");
		response.then().log().all();
		return response.jsonPath().getString("cobrandConversationCredentials.sessionToken");
	}

	public static String testlogin(String cobSession, String loginName, String password) throws Exception {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cobSessionToken", cobSession);
		params.put("login", loginName);
		params.put("password", password);

		Response login = RestUtil.post(params,
				"https://rest.autopfmc.corp.yodlee.com:3443/services/srest/ymc/v1.0/authenticate/login");
		login.then().log().all();
		return login.jsonPath().getString("userContext.conversationCredentials.sessionToken");
	}

	public static void testUnregisterUser(String cobSession, String userSession) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cobSessionToken", cobSession);
		params.put("userSessionToken", userSession);

		Response logout = RestUtil.post(params,
				"https://rest.autopfmc.corp.yodlee.com:3443/services/srest/ymc/v1.0/jsonsdk/UserRegistration/unregister");
		logout.then().log().all();
	}

	public static void main(String[] args) throws Exception {
		InputStream fis = RestUtil.class.getClassLoader().getResourceAsStream("EnvironmentDetails/AutoNPR.Properties");
		try {
			properties.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(properties.getProperty("coBrandLogin"));
		String cobrandSession = getCobrandSessionToken();// "YCOM09043403541"
		String userSession = testlogin(cobrandSession, "YCOM09043403541", "Test@123"); // YCOM1467120433712
																						// "YCOM09043403541"
		System.out.println("Login Successfull");
		testUnregisterUser(cobrandSession, userSession);
	}

	public static void testExecuteJMXClient() {
		System.out.println("Executing JMXClient...");

		String DCIP = properties.getProperty("DCIP");
		String DCPort = properties.getProperty("DCPort");
		String RestIP_1 = properties.getProperty("RestIP");

		String RestPort = properties.getProperty("RestPort");
		System.out.println("DCIP : " + DCIP + "\tDCPort : " + DCPort);
		System.out.println("RestIP : " + RestIP_1 + "\tRestPort : " + RestPort);

		boolean DCContentCache = Client.initiateCobrandReCache("contentcache", DCIP, DCPort, "123", "", "");
		boolean DCCobrandCache = Client.initiateCobrandReCache("cobrandcache", DCIP, DCPort, "123", "", "");
		boolean RestContentCache = Client.initiateCobrandReCache("contentcache", RestIP_1, RestPort, "123", "", "");
		boolean RestCobrandCache = Client.initiateCobrandReCache("cobrandcache", RestIP_1, RestPort, "123", "", "");

		System.out.println("DCContentCache : " + DCContentCache + "\tDCCobrandCache : " + DCCobrandCache);
		System.out.println("RestContentCache : " + RestContentCache + "\tRestCobrandCache : " + RestCobrandCache);

		String RestIP_2;

		if (properties.getProperty("baseURI").contains("rest.autopfmc")) {
			RestIP_2 = properties.getProperty("RestIP_2");
			System.out.println("RestIP_2 : " + RestIP_2);

			boolean RestContentCache_2 = Client.initiateCobrandReCache("contentcache", RestIP_2, RestPort, "123", "",
					"");
			boolean RestCobrandCache_2 = Client.initiateCobrandReCache("cobrandcache", RestIP_2, RestPort, "123", "",
					"");

			System.out.println(
					"RestContentCache_2 : " + RestContentCache_2 + "\tRestCobrandCache_2 : " + RestCobrandCache_2);
		}
	}

	/*
	 * Update cobrand_acl_value table with desired value of the param_acl for the
	 * cobrand
	 */
	public static void updateCobrandACLValue(String paramACL, String desiredFeatureKeyValue) throws SQLException {
		System.out.println("\nUpdateCobrandACLValue");
		String cobrandId = properties.getProperty("cobrandId");

		String paramACLCheck = verifyParamACLValue(paramACL);

		if (!paramACLCheck.equalsIgnoreCase(desiredFeatureKeyValue)) {
			System.out.println("Updating cobrand_acl_value as '" + desiredFeatureKeyValue + "' for param_acl '"
					+ paramACL + "' in COBRAND_ACL_VALUE table.");

			Connection con = getConnection();
			Statement stmt = con.createStatement();
			stmt.executeQuery("update cobrand_acl_value set acl_value = '" + desiredFeatureKeyValue
					+ "' where cobrand_id = '" + cobrandId
					+ "' and param_acl_id in (select param_acl_id from param_acl where acl_name like '" + paramACL
					+ "')");
			testExecuteJMXClient();
			con.close();

		} else {
			System.out.println(
					"The acl_value is same as desired value in COBRAND_ACL_VALUE table : " + desiredFeatureKeyValue);
		}
	}

	/*
	 * Check the value of any param_acl in param_acl and cobrand_acl_value tables
	 * Insert row into cobrand_acl_value table if no entry is present
	 */
	public static String verifyParamACLValue(String aclName) throws SQLException {

		System.out.println("\nCheck value for ParamACL '" + aclName + "'");
		String cobrandId = properties.getProperty("cobrandId");

		String result = "";
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		String id = null;
		String acl_value = null;

		ResultSet resultset = stmt.executeQuery(
				"select PARAM_ACL_ID as PARAM_ACL_ID, ACL_NAME as ACL_NAME, ACL_VALUE as ACL_VALUE from PARAM_ACL where ACL_NAME like '"
						+ aclName + "'");
		if (!resultset.next()) {
			System.out.println("No data to fetch.");

		} else {
			acl_value = resultset.getString("ACL_VALUE");
			id = resultset.getString("PARAM_ACL_ID");
			System.out.println("Value of '" + aclName + "'" + " in PARAM_ACL = '" + acl_value + "'");
		}

		ResultSet resultset1 = stmt.executeQuery(
				"select PARAM_ACL_ID as PARAM_ACL_ID, ACL_VALUE as ACL_VALUE from cobrand_acl_value where cobrand_id = '"
						+ cobrandId + "' and param_acl_id = '" + id + "'");
		if (!resultset1.next()) {
			System.out.println("No data to fetch. Inserting value in COBRAND_ACL_VALUE table...");

			ResultSet resultset2 = stmt.executeQuery(
					"insert into cobrand_acl_value values ((select max(cobrand_acl_value_id) from cobrand_acl_value)+1,"
							+ cobrandId + "," + id + ",'" + acl_value
							+ "',sysdate,sysdate,null,null,null,null,1,null);");
			testExecuteJMXClient();

		} else {
			String default_value = resultset1.getString("ACL_VALUE");

			System.out.println("Value in COBRAND_ACL_VALUE = '" + default_value + "'");
			result = default_value;
		}
		stmt.close();

		return result;
	}

	public static Connection getConnection() {

		String dbUserName = properties.getProperty("dbUserName");
		String dbPassword = properties.getProperty("dbPassword");
		String dbHost = properties.getProperty("dbHost");

		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(dbHost, dbUserName, dbPassword);
			System.out.println("Connected to database");

		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("SQLException");
			e.printStackTrace();
		}
		return con;
	}

	/*
	 * Update the cob_param table with desired value of the param_key for the
	 * cobrand
	 */
	public static void updateCobParamValue(String paramKey, String desiredFeatureKeyValue) throws SQLException {
		System.out.println("\nUpdateCobParamValue");
		String cobrandId = properties.getProperty("cobrandId");

		String paramKeyCheck = verifyParamKeyValue(paramKey);

		if (!paramKeyCheck.equalsIgnoreCase(desiredFeatureKeyValue)) {
			System.out.println("Updating param_value as '" + desiredFeatureKeyValue + "' for param_key '" + paramKey
					+ "' in COB_PARAM table.");

			Connection con = getConnection();
			Statement stmt = con.createStatement();
			stmt.executeQuery("update cob_param set param_value = '" + desiredFeatureKeyValue + "' where cobrand_id = '"
					+ cobrandId
					+ "' and param_key_id in (select param_key_id from param_key where param_key_name like '" + paramKey
					+ "')");
			testExecuteJMXClient();
			con.close();

		} else {
			System.out
					.println("The param_value is same as desired value in COB_PARAM table : " + desiredFeatureKeyValue);
		}
	}

	public static String verifyParamKeyValue(String paramKey) throws SQLException {
		System.out.println("\nCheck value for param_key '" + paramKey + "'");

		String cobrandId = properties.getProperty("cobrandId");
		String value = "";
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		String id = null;
		String default_value = null;

		ResultSet resultset = stmt.executeQuery(
				"select PARAM_KEY_ID as KEY_ID, PARAM_KEY_NAME as KEY_NAME, DEFAULT_VALUE as VALUE from PARAM_KEY where PARAM_KEY_NAME like '"
						+ paramKey + "'");
		if (!resultset.next()) {
			System.out.println("No data to fetch.");

		} else {
			default_value = resultset.getString("VALUE");
			id = resultset.getString("KEY_ID");
			System.out.println("Default value of PARAM_KEY '" + paramKey + "'" + " = '" + default_value + "'");
		}

		ResultSet resultset1 = stmt.executeQuery(
				"select PARAM_VALUE as PARAM_VALUE, COBRAND_ID as COBRAND_ID, PARAM_KEY_ID as KEY_ID from COB_PARAM where COBRAND_ID = '"
						+ cobrandId + "' and PARAM_KEY_ID = '" + id + "'");
		if (!resultset1.next()) {
			System.out.println("No data to fetch. Inserting value into COB_PARAM table...");

			ResultSet resultset2 = stmt
					.executeQuery("insert into COB_PARAM values((select max(COB_PARAM_ID) from COB_PARAM)+1,'"
							+ default_value + "'," + cobrandId + "," + id + ",null,null,null)");
			testExecuteJMXClient();

		} else {
			String defaultValue = resultset1.getString("PARAM_VALUE");
			System.out.println("Value in COB_PARAM = '" + defaultValue + "'");

			value = defaultValue;
		}
		return value;
	}

}
