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

import static org.testng.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONObject;
import org.testng.AssertJUnit;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.URLConstants;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;

import io.restassured.response.Response;

public class SegmentationHelper {

	public String registerSegmentName(String loginName, String segmentNameKey, String segmentNameValue) {

		JSONObject userObj = new JSONObject();
		userObj.put(segmentNameKey, segmentNameValue);
		userObj.put("loginName", loginName);
		userObj.put("password", "TEST@123");
		userObj.put("email", "yodlee@yodlee.com");
		JSONObject createUserObj = new JSONObject();
		createUserObj.put("user", userObj);
		String createUserObjJSON = createUserObj.toString();
		System.out.println("createUserObj is : " + createUserObj);
		return createUserObjJSON;

	}

	public String updateSegmentAttribute(String segmentNameKey, String segmentNameValue) {
		JSONObject userObj = new JSONObject();
		userObj.put(segmentNameKey, segmentNameValue);
		JSONObject createUserObj = new JSONObject();
		createUserObj.put("user", userObj);
		String createUserObjJSON = createUserObj.toString();
		System.out.println("createUserObj is : " + createUserObj);
		return createUserObjJSON;

	}

	public String verifyMemSegHistroy(long mem_id) {

		String query = "select prev_mem_segments_id from mem_segments_history where mem_id=" + mem_id
				+ " and cobrand_id=" + Configuration.getInstance().getCobrandObj().getCobrandId()
				+ " order by row_created desc";
		String segmentId = null;
		DbHelper dbHelper = new DbHelper();
		try {
			segmentId = dbHelper.getValueFromDB(query);
			System.out.println("Previous mem SegmentId is : " + segmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return segmentId;
	}

	public String getUserDetils(String userSession) {
		 UserUtils util = new UserUtils();
		 Session session = Configuration.getInstance().getCobrandSessionObj();
		 session.setPath(Configuration.getInstance().getCobrandSessionObj().getPath() + URLConstants.USER_GET_AND_UPDATE);
		
		 Response response = util.getUserDetails(session);
		
		String responseSegmentName = response.jsonPath().getString("user.segmentName");
		int getstatusCode = response.statusCode();
		AssertJUnit.assertEquals("GET User Details sucessfully retrived", 200, getstatusCode);
		assertEquals(getstatusCode, 200, "GET User Details");
		return responseSegmentName;
	}

	public ArrayList<String> getValue(String query, String column, String column1) throws SQLException {
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();

		ArrayList<String> array = new ArrayList<>();
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String str = resultSet.getString(column);
				String str1 = resultSet.getString(column1);
				array.add(str);
				array.add(str1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, statement, resultSet);
		}
		return array;
	}

}
