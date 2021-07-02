/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import com.omni.pfm.Exceptions.LabelNotFoundException;
import com.omni.pfm.config.Config;
import com.omni.pfm.constants.DataConstants;
import com.omni.pfm.constants.PropertyConstants;
import com.omni.pfm.listeners.EReporter;
import com.relevantcodes.extentreports.LogStatus;

public class PropsUtil {
	private static Logger logger = LoggerFactory.getLogger(PropsUtil.class);
	private static Config config = Config.getInstance();
	public static Map<String, Map<String, String>> componentMap = new HashMap<String, Map<String, String>>();
	public static Map<String, String> detailsMap = new HashMap<>();

	public static String[] getLocator(String lname) {
		Config config = Config.getInstance();
		Map<String, String> lmap;
		if (lname == null || lname.trim().equals("")) {
			logger.error("Locator name is null or empty : {}", lname);
			return null;
		}
		if (!config.dataMap.containsKey(DataConstants.LOCATOR_MAP)) {
			logger.error("No locator map found in data map : {}", config.dataMap);
			return null;
		} else {
			lmap = config.dataMap.get(DataConstants.LOCATOR_MAP);
			if (!lmap.containsKey(lname)) {
				logger.error("Locator map does not contain the - {} - locator info : {}", lname, lmap);
				return null;
			} else {
				String[] linfo = lmap.get(lname).split(">", 2);
				logger.info("locator {} : {}", lname, linfo);
				return linfo;
			}
		}
	}

	public static void init() {
		// Health
		String[] componentName = { "DAPGatherer", "DCServlets", "DBFiler", "RestServer", "restServerLB", "YSL",
				"appsCenter", "MoneyCenter", "moneyCenterLB", "wealthPortfoliosLB", "MessagingService", "Node",
				"MongoService", "autoPfmAppMongoServicesLB", "Alert", "RealEstate", "AddAcoountSiteLevel",
				"AddAccountContentLevel", "YCC", "NewSDk" };
		String componentDetails;
		for (int i = 0; i < componentName.length; i++) {

			componentDetails = PropsUtil.getEnvPropertyValue((componentName[i]));
			System.out.println("componentDetails:" + componentDetails);
			populateComponentMap(componentName[i], componentDetails);
			System.out.println("componentMap:" + componentMap);
		}

	}

	private static void populateComponentMap(String component, String componentDetail) {
		String[] strings = { "url", "port", "host", "username", "password", "dbName", "gathererType" };
		String[] details = componentDetail.split(",");
		detailsMap = new HashMap<>();
		if (component.equalsIgnoreCase("AddAcoountSiteLevel") || component.equalsIgnoreCase("AddAccountContentLevel")) {
			detailsMap.put("url", details[0]);
			detailsMap.put("cobName", details[1]);
			detailsMap.put("cobPassword", details[2]);
		} else {
			for (int i = 0; i < strings.length; i++) {
				if (!details[i].equalsIgnoreCase("null")) {
					detailsMap.put(strings[i], details[i]);
				}
			}
		}

		componentMap.put(component, detailsMap);

	}

	public static WebElement getWebElement(WebDriver driver, String locatorLabel) {
		WebElement element = null;
		String lvalues[] = getLocator(locatorLabel);
		if (lvalues == null) {
			logger.error("Not able to find the value of : {} from locator map", locatorLabel);
			return null;
		} else if (lvalues.length < 2) {
			logger.error("Locator value is not proper {} = {}", locatorLabel, lvalues);
			return null;
		} else {
			String by = lvalues[0];
			logger.info("Value of By is : {}", by);
			logger.info("Locator value is : {}", lvalues[1]);
			try {
				switch (by) {
				case "id":
					System.out.println("Inside switch id");
					// wait.until(ExpectedConditions.elementToBeClickable(By.id(lvalues[1])));
					element = driver.findElement(By.id(lvalues[1]));
					System.out.println(element);
					break;
				case "name":
					element = driver.findElement(By.name(lvalues[1]));
					break;
				case "xpath":
					element = driver.findElement(By.xpath(lvalues[1]));
					break;
				case "css":
					element = driver.findElement(By.cssSelector(lvalues[1]));
					break;
				case "link":
					element = driver.findElement(By.linkText(lvalues[1]));
					break;
				case "partialLink":
					element = driver.findElement(By.partialLinkText(lvalues[1]));
					break;
				case "tag":
					element = driver.findElement(By.tagName(lvalues[1]));
					break;
				case "className":
					element = driver.findElement(By.className(lvalues[1]));
					break;
				}
			} catch (NoSuchElementException e) {
				logger.error("Unable to locate the element with [{}] = [{}]", lvalues[0], lvalues[1]);
			}
			if (element == null) {
				logger.error("Could not Locate Element with [{} = {}]", lvalues[0], lvalues[1]);
			}
			return element;
		}
	}

	/*
	 * public static void main(String[] args) { Config config =
	 * Config.createInstance("BBT"); PropsUtil.getDataPropertyValue("categoryAtm");
	 * }
	 */
	public static String getDataPropertyValue(String dataLabel) {
		config = Config.getInstance();
		String value = "";
		int status = 0;
		if (GenericUtil.isNull(dataLabel)) {
			Reporter.log("Data Label [" + dataLabel + "] is empty...");
			logger.error("Data label :: '{}' is null or empty", dataLabel);
		}
		if (config.dataMap.containsKey(DataConstants.DATA_LABELS_MAP)) {
			if (MobileUtil.isMobileFlag()) {
				String keyValue = dataLabel + "_" + PropertyConstants.MOBILE_DATA_SUFFIX;
				if (config.dataMap.get(DataConstants.DATA_LABELS_MAP).containsKey(keyValue)) {
					value = config.dataMap.get(DataConstants.DATA_LABELS_MAP).get(keyValue);
					logger.info("Value of label [{}] from property file is : {}", keyValue, value);
					status = 1;
				} else if (status == 0) {
					if (config.dataMap.get(DataConstants.DATA_LABELS_MAP).containsKey(dataLabel)) {
						value = config.dataMap.get(DataConstants.DATA_LABELS_MAP).get(dataLabel);
						logger.info("Value of label [{}] from property file is : {}", dataLabel, value);
					} else {
						logger.error("Data Label [{}] or [{}] not found in map", dataLabel, keyValue);
						throw new LabelNotFoundException("Could not find the label '" + dataLabel + "' or label "
								+ keyValue + " in the data property file.");
					}
				} else {
					logger.error("Data Label [{}] or [{}] not found in map", dataLabel, keyValue);
					Reporter.log("Data Label [" + dataLabel + "] not found...");
					throw new LabelNotFoundException("Could not find the label '" + dataLabel + "' or label " + keyValue
							+ " in the data property file.");
				}

			} else {
				if (config.dataMap.get(DataConstants.DATA_LABELS_MAP).containsKey(dataLabel)) {
					value = config.dataMap.get(DataConstants.DATA_LABELS_MAP).get(dataLabel);
					logger.info("Value of label [{}] from property file is : {}", dataLabel, value);
				} else {
					logger.error("Data Label [{}] not found in map", dataLabel);
					Reporter.log("Data Label [" + dataLabel + "] not found...");
					throw new LabelNotFoundException(
							"Could not find the label '" + dataLabel + "' in the data property file.");
				}
			}
		} else {
			logger.error("DATA_LABELS_MAP not found");
		}
		return value;
	}

	public static String getEnvPropertyValue(String dataLabel) {
		String value = "";
		config = Config.getInstance();
		if (GenericUtil.isNull(dataLabel)) {
			logger.info("Label '{}' is null or empty", dataLabel);
			return value;
		}
		if (config.dataMap.containsKey(DataConstants.ENVIRONMENT_MAP)) {
			if (config.dataMap.get(DataConstants.ENVIRONMENT_MAP).containsKey(dataLabel)) {
				value = config.dataMap.get(DataConstants.ENVIRONMENT_MAP).get(dataLabel);
				logger.info("Value of label [{}] from property file is : {}", dataLabel, value);
			} else {
				logger.error("Data Label [{}] not found in map", dataLabel);
				throw new LabelNotFoundException(
						"Could not find the label '" + dataLabel + "' in the env property file.");
			}
		} else
			logger.error("DATA_LABELS_MAP not found");
		return value;
	}

	public boolean setDataPropertyValue(String key, String value) {
		return true;
	}

	public static String setEnvPropertyValue(String key, String value) {
		return setProperty("./src/main/resources/EnvironmentDetails/YCOM.properties", key, value);
	}

	public static String setProperty(String filePath, String key, String value) {
		InputStream in = null;
		try {
			in = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e1) {
			logger.error("Error trying to open the file - " + filePath);
			logger.error("Exception : " + e1.getMessage());
			EReporter.log(LogStatus.INFO, "Error trying to open the file - " + filePath);
			return null;
		}
		Properties props = new Properties();
		try {
			props.load(in);
			in.close();
			String oldVal = (String) props.setProperty(key, value);
			logger.info("Old Value of the key {} = {}", key, oldVal);
			File f = new File(filePath);
			if (!f.canWrite()) {
				f.setWritable(true);
			}
			OutputStream out = new FileOutputStream(new File(filePath));
			props.store(out, "Last Run User");
			out.close();
			return oldVal;
		} catch (IOException e) {
			logger.error("Error while setting the property value of key - {}", key);
			logger.error("Exception : " + e.getMessage());
			EReporter.log(LogStatus.INFO, "Error while setting the property value of key - " + key);
			return null;
		}
	}
}
