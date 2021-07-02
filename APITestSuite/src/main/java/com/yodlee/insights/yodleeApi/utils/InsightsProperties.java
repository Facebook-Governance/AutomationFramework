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
package com.yodlee.insights.yodleeApi.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.yodlee.yodleeApi.common.Configuration;

public class InsightsProperties {	
	
	private  String fileName = null;
	private final String nonDockerFileName = "\\src\\test\\resources\\InsightsDataNonDocker.properties";
	private final String dockerFileName = "\\src\\test\\resources\\InsightsDataDocker.properties";
	Configuration configurationObj = Configuration.getInstance();
	
	  public  Properties readPropertiesFile() throws IOException {
		  if(configurationObj.getEnv().equalsIgnoreCase("Docker")) {
			  fileName = dockerFileName;
		  }
		  if(configurationObj.getEnv().equalsIgnoreCase("NonDocker")) {
			  fileName = nonDockerFileName;
		  }
	      FileInputStream fis = null;
	      Properties prop = null;
	      try {
	         fis = new FileInputStream(System.getProperty("user.dir")+fileName);
	         prop = new Properties();
	         prop.load(fis);
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	         fis.close();
	      }
	      return prop;
	   }
	}

