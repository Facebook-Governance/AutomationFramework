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

import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yodlee.yodleeApi.pojo.HttpMethodParameters;

public class GroupsHelper {

	public String createGroupJson(String group) throws JSONException {
		JSONObject grpObj = new JSONObject();
		JSONArray grpObj1 = new JSONArray();
		JSONObject grpObj2 = new JSONObject();
		grpObj.put("name", group);
		grpObj1.put(grpObj);
		grpObj2.put("group", grpObj1);
		String userRegisterObjJSON = grpObj2.toString();
		System.out.println("userRegisterObjJSON is : " + userRegisterObjJSON);
		return userRegisterObjJSON;
	}
	
	//Soujanya modified this method.
	public String addAccountGroupJson(Long accountID) throws JSONException {
		JSONObject grpObj = new JSONObject();
		JSONArray grpObj1 = new JSONArray();
		JSONObject grpObj2 = new JSONObject();
		JSONObject grpObj3 = new JSONObject();
		JSONArray grpObj4 = new JSONArray();
		grpObj.put("id", accountID);
		grpObj1.put(grpObj);
		grpObj2.put("account", grpObj1);
		grpObj4.put(grpObj2);
		grpObj3.put("group", grpObj4);
		String userRegisterObjJSON1 = grpObj3.toString();
		System.out.println("userRegisterObjJSON1 is : " + userRegisterObjJSON1);
		return userRegisterObjJSON1;
	}
	
	public String updateGroupJson(String group) throws JSONException{
		JSONObject grpObj = new JSONObject();
		JSONArray grpObj1= new JSONArray();
		JSONObject grpObj2 = new JSONObject();
		grpObj.put("name", group);
		grpObj1.put(grpObj);
		grpObj2.put("group", grpObj1);
		String RegisterObjJSON = grpObj2.toString();
        System.out.println("updateGroupJson is : " + RegisterObjJSON);
        return RegisterObjJSON;
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
	
	public String emptyListAccountGroup(Long accountID) throws JSONException {
		JSONObject grpObjid = new JSONObject();
		JSONObject grpObjaccount = new JSONObject();
		JSONObject grpObjgroup = new JSONObject();

		JSONArray grpObjAccArray = new JSONArray();
		JSONArray grpObjGroupArray = new JSONArray();

		/*
		 * grpObjid.put("id", accountID); grpObjAccArray.put(grpObjid);
		 */

		grpObjaccount.put("account", grpObjAccArray);
		grpObjGroupArray.put(grpObjaccount);

		grpObjgroup.put("group", grpObjGroupArray);

		String userRegisterObjJSON = grpObjgroup.toString();
		System.out.println("grpObjgroup 1 is : " + userRegisterObjJSON);

		return userRegisterObjJSON;
	}
	
	public String addAccountGroupJsonInvalid(Long accountID) throws JSONException {
		JSONObject grpObjid = new JSONObject();
		JSONObject grpObjaccount = new JSONObject();
		JSONObject grpObjgroup = new JSONObject();

		JSONArray grpObjAccArray = new JSONArray();
		JSONArray grpObjGroupArray = new JSONArray();

		grpObjid.put("id", accountID);
		grpObjAccArray.put(grpObjid);

		grpObjaccount.put("account", grpObjAccArray);
		grpObjGroupArray.put(grpObjaccount);

		grpObjgroup.put("group", grpObjGroupArray);

		String userRegisterObjJSON = grpObjgroup.toString();
		System.out.println("grpObjgroup 1 is : " + userRegisterObjJSON);
		return userRegisterObjJSON;
	}
	
	public String addAccountGroupJsonAccounts(Long accountID) throws JSONException {
		JSONObject grpObjid = new JSONObject();
		JSONObject grpObjaccount = new JSONObject();
		JSONObject grpObjgroup = new JSONObject();

		JSONArray grpObjAccArray = new JSONArray();
		JSONArray grpObjGroupArray = new JSONArray();

		grpObjid.put("id", accountID);
		grpObjAccArray.put(grpObjid);

		grpObjaccount.put("account", grpObjAccArray);
		grpObjGroupArray.put(grpObjaccount);

		grpObjgroup.put("group", grpObjGroupArray);

		String userRegisterObjJSON = grpObjAccArray.toString();
		System.out.println("grpObjgroup 1 is : " + userRegisterObjJSON);
		System.out.println("grpObjgroup 1 is : " + userRegisterObjJSON);

		return userRegisterObjJSON;
	}
	
	public String createEmptyGroupJson(Long accountID) throws JSONException {
		JSONObject grpObj = new JSONObject();
		JSONObject grpObj2 = new JSONObject();

		JSONArray grpObj1 = new JSONArray();
		/*
		 * grpObj.put("name", group); grpObj1.put(grpObj);
		 */
		grpObj2.put("group", grpObj1);
		String RegisterObjJSON = grpObj2.toString();
		System.out.println("updateGroupJson is Empty : " + RegisterObjJSON);
		return RegisterObjJSON;

		// "group":[{"name":"test123"}]}
	}
}
