/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.insights.yodleeApi.utils.v2;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class InsightsObjectsFactory {	
	
	JsonParser jsonParser = new JsonParser();
	JsonObject inisghtsKeysObjects = null;
	
	
	public Object getInstances(String type) {
		
		if(type == null || type.isEmpty()) {
			return null;
		}
		if(type.equalsIgnoreCase("expectedKeys")) {
			try {
				inisghtsKeysObjects = (JsonObject) jsonParser.parse(new FileReader(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\TestData\\CSVFiles\\Insights\\InsightsKeys\\ExpectedInsightsKeys.json"));
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {			
				e.printStackTrace();
			}
		}
		else if(type.equalsIgnoreCase("mongoConnection")) {
			
		}
			
		return null;
	}
	
	
	
	
}
