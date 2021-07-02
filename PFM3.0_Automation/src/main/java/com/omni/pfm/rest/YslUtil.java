/**
 * Copyright (c) 2014 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.omni.pfm.rest;

import java.nio.charset.Charset;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response; 

public class YslUtil {

	public static Response post(String url) {
		
		Response response = RestAssured.
				 given()
				 .log()
				 .all()
				 .contentType(ContentType.JSON)
				 .post(url);
		return response;
	}
	public static Response putBodyQuery(String header,String params,Map<String,String> queryParams,String path) {
		Response response = RestAssured
				.given()
				.config(RestAssuredConfig.config().encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).relaxedHTTPSValidation()
				.header("Authorization",header).contentType(ContentType.JSON)
				.log()
				.all()
				.body(params)
				.queryParams(queryParams)
				.put(path);
				
		
		return response;
	}
	public static Response postQuery(String header,Map<String,String> params,String path) {
		Response response = RestAssured
				.given()
				.config(RestAssuredConfig.config().encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).relaxedHTTPSValidation()
				.header("Authorization",header).contentType(ContentType.JSON)
				.log()
				.all()
				.queryParameters(params)
				.post(path);
				
		
		return response;
	}
	
	
	public static Response postQuery(String header,String path) {
		Response response = RestAssured
				.given()
				.config(RestAssuredConfig.config().encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).relaxedHTTPSValidation()
				.header("Authorization",header).contentType(ContentType.JSON)
				.log()
				.all()
				.get(path);
				
		
		return response;
	}
	
	
	public static Response postBody(String header,String params,String path,String cobrand,String apiVersion) {
		Response response = RestAssured
				.given()
				.config(RestAssuredConfig.config().encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).relaxedHTTPSValidation()
				.header("Authorization",header).header("Cobrand-Name", cobrand).header("Api-Version",apiVersion).contentType(ContentType.JSON)
				.log()
				.all()
				.body(params)
				.post(path);
				
		
		return response;
	}
	public static Response postCobLogin(String params,String path,String cobrand,String apiVersion) {
		Response response = RestAssured
				.given()
				.config(RestAssuredConfig.config().encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).relaxedHTTPSValidation()
				.contentType(ContentType.JSON)
				.log()
				.all()
				.body(params).post(path);
		return response;
	}
	public static Response post(Map<String, String> params,String path,String cobrand,String apiVersion) {
		Response response = RestAssured
				.given()
				.header("Cobrand-Name", cobrand).header("Api-Version",apiVersion).contentType("application/json")
				.log()
				.all()
				.formParams(params).post(path);
		return response;
	}
	
public static Response getWithAuthorizationHeader(String path,String header,String cobrand,String apiVersion) {
		
		Response response = RestAssured
				.given()
				.header("Authorization",header).header("Cobrand-Name", cobrand).header("Api-Version",apiVersion)
				//.log()
				//.all()
				.get(path);
		return response;
	}
	public static Response get(Map<String, String> params, String path) {
		
		Response response = RestAssured
				.given()
				.log()
				.all()
				.queryParams(params)
				.header("Accept","application/json")
				.get(path);
		return response;
	}
	
	public static Response putBodyQuery(String header,String params,String path) {
		System.out.println(params);
		Response response = RestAssured
				.given()
				.config(RestAssuredConfig.config().encoderConfig(new EncoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).relaxedHTTPSValidation()
				.header("Authorization",header).contentType(ContentType.JSON)
				.log()
				.all()
				.body(params)
				.put(path);
		System.out.println(response);
				return response;
	}
	
	

}
