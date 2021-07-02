/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.dom4j.tree.SingleIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.XmlBeans.Page;
import com.omni.pfm.XmlBeans.Pages;
import com.omni.pfm.constants.DataConstants;
import com.omni.pfm.constants.PropertyConstants;
import com.omni.pfm.utility.MobileUtil;
import com.omni.pfm.utility.PropsUtil;

public final class Config {
	private static Logger logger = LoggerFactory.getLogger(Config.class);
	public Map<String, Map<String, String>> dataMap = new HashMap<>();

	// contains list of all the pages and their elements in the page xml file
	public List<Page> pageRepo;

	// contains mapping of pages with its corresponding elements
	public Map<String, Page> pageRepository = new HashMap<>();
	private String environment = "YCOM"; // default is YCOM
	private static Config singletonInstance;
	private String currentPage;
	private String currentFrame;
	private String currentUser;
	public static String userName = null;
	public static String appFlag = null;

	public static void setAppFlag(String appFlag) {
		Config.appFlag = appFlag;
	}

	public boolean isIFrameEnabled() {
		if (PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled").equalsIgnoreCase("yes")) {
			return true;
		} else
			return false;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public static void setUserName(String userName) {
		Config.userName = userName;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	private Config() {
		System.out.println("hi");
	}

	private Config(String env) {
		// DO NOT CHANGE THE SEQUENCE OF METHOD CALLS
		logger.info("==> Entering Config private Constructor");
		this.getEnvDetails(env);
		this.loadDataLabelsAsMap();
		this.loadAllPagesFromXml(this.environment + PropertyConstants.XML_File_Suffix);
		logger.info("<= Exiting Config private Constructor");
	}

	public void setCurrentPage(String pageName) {
		this.currentPage = pageName;
	}

	public String getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentFrame(String frameName) {
		this.currentFrame = frameName;
	}

	public String getCurrentFrame() {
		return this.currentFrame;
	}

	public static Config getInstance() {
		return singletonInstance;
	}

	public static Config createInstance(String env, String appFlag) {
		logger.info("==>Entering createInstance()");
		if (singletonInstance == null) {
			synchronized (Config.class) {
				if (singletonInstance == null) {
					logger.info("Creating new Instance of Config class");
					singletonInstance = new Config(env);
					MobileUtil.setTypeOfMode(appFlag);
					MobileUtil.mobileSuiteParser();
				}
			}
		}
		logger.info("Returning Config instance");
		return singletonInstance;
	}

	private Map<String, String> getEnvDetails(String env) {
		logger.info("==> Entering Config.getEnvDetails() method.");
		Map<String, String> envDetails = new HashMap<>();
		InputStream inputStream;
		Properties prop = new Properties();
		this.environment = env;
		if (env == null || env.trim().equals("")) {
			logger.error("Environment from the property file is null or empty : {}", env);
		}

		else {
			logger.info("Environment is : {}", env);
			String fpath = PropertyConstants.EnvironmentDetails_Path + File.separator + env + ".properties";
			inputStream = Config.class.getClassLoader().getResourceAsStream(fpath);
			logger.info("Evironment Properties File is : {}", fpath);
			try {
				if (inputStream != null) {
					logger.info("Successfully loaded the " + env + ".properties file");
					prop.clear();
					prop.load(inputStream);
				} else {
					logger.error("Could not load the" + env + ".properties file");
				}
			} catch (IOException ex) {
				logger.error("Could not read the" + env + ".properties file : {}", ex);
			}
			Set<Object> keys = prop.keySet();
			System.out.println(keys.size());
			if (keys.size() < 1) {
				logger.error("No values present in the " + env + ".properties file");
			}

			else {
				for (Object key : keys) {
					envDetails.put((String) key, prop.getProperty((String) key));
				}
			}

		}
		logger.info("envDetails map value is : {}", envDetails);
		logger.info("<== Exiting Config.getEnvDetails() method.");
		this.dataMap.put(DataConstants.ENVIRONMENT_MAP, envDetails);
		return envDetails;
	}

	@Deprecated
	@SuppressWarnings(value = { "unused" })
	private void loadLocatorLabels() {
		logger.info("==> Entering loadLocatorLabels method");
		final String env = this.environment;
		if (env == null || env.trim().equals("")) {
			logger.error("Environment value is null or empty : {}", env);
			return;
		} else {
			String fname = env + "_locators.properties";
			String propertyFileFullPath = PropertyConstants.Locator_Labels_Path + File.separator + fname;
			this.dataMap.put(DataConstants.LOCATOR_MAP, loadPropertiesAsMap(propertyFileFullPath));
			logger.info("Loaded locatorLabelsMap into the dataMap : {}", this.dataMap.get(DataConstants.LOCATOR_MAP));
			logger.info("<== Exiting loadLocatorLabels method");
		}
	}

	private Map<String, String> loadPropertiesAsMap(String propertyFileFullPath) {
		logger.info("==> Entering loadPropertiesAsMap() method.");
		InputStream inputStream;
		Properties prop = new Properties();
		if (propertyFileFullPath == null || propertyFileFullPath.trim().equals("")) {
			logger.error("Property file name is null or empty : {}", propertyFileFullPath);
			logger.error("<== Returning from loadPropertiesAsMap method with null");
			return null;
		} else {
			logger.info("Property file full path is : {}", propertyFileFullPath);
		}
		try {
			if (!new File(Config.class.getClassLoader().getResource(propertyFileFullPath).toURI()).exists()) {
				logger.error("FILE DOES NOT EXIST : {}", propertyFileFullPath);
				logger.error("Returning from loadPropertiesAsMap.Returning with null");
				return null;
			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			logger.error("Unable to find the file. Path is incorrect");
			logger.error("Returning from loadPropertiesAsMap.Returning with null");
			return null;
		}
		inputStream = Config.class.getClassLoader().getResourceAsStream(propertyFileFullPath);
		try {
			if (inputStream != null) {
				logger.info("Successfully loaded the {} file", propertyFileFullPath);
				prop.clear();
				prop.load(inputStream);
			} else {
				logger.error("Could not load the {} properties file", propertyFileFullPath);
			}
		} catch (IOException ex) {
			logger.error("Could not read the {} properties file : {}", propertyFileFullPath, ex);
		}

		Set<Object> keys = prop.keySet();
		System.out.println(keys.size());
		Map<String, String> propMap = new HashMap<>();
		if (keys.size() < 1) {
			logger.error("No values present in the {} properties file", propertyFileFullPath);
			logger.info("<== Exiting loadPropertiesAsMap method with empty map : {}", propMap);
			return propMap;
		}

		else {
			for (Object key : keys) {
				// System.out.println((String) key + " = "+
				// prop.getProperty((String)key));
				propMap.put((String) key, prop.getProperty((String) key));
			}
		}
		logger.info("<== Exiting loadPropertiesAsMap method");
		return propMap;
	}

	public String getEnvironment() {
		return this.environment;
	}

	private void loadDataLabelsAsMap() {
		logger.info("==> Entering Config.loadDataLabelsAsMap method.");
		String env = this.environment;
		logger.info("Environment is : {}", env);
		String dataLabelsFilePath = PropertyConstants.Data_Labels_Path + File.separator + env
				+ PropertyConstants.Data_File_Suffix;
		logger.info("Data Label Property File path is : {}", dataLabelsFilePath);
		Map<String, String> dataLabelsMap = loadPropertiesAsMap(dataLabelsFilePath);
		if (dataLabelsMap == null) {
			logger.error("Data Labels Map is null : {} - Returning with null", dataLabelsMap);
			this.dataMap.put(DataConstants.DATA_LABELS_MAP, dataLabelsMap);
			return;
		} else {
			this.dataMap.put(DataConstants.DATA_LABELS_MAP, dataLabelsMap);
		}
		logger.info("<== Exiting Config.loadDataLabelsAsMap method.");

	}

	private void loadAllPagesFromXml(String xmlFileName) {
		logger.info("==> Entering loadAllPagesFromXml() method. ");
		List<Page> pages = null;
		logger.info("PAGE XML FILE IS : {}", xmlFileName);
		String fullPath = PropertyConstants.Page_XML_Path + File.separator + xmlFileName;

		try {
			if (!new File(Config.class.getClassLoader().getResource(fullPath).toURI()).exists()) {
				logger.error("XML FILE DOES NOT EXIST : {}", fullPath);
				logger.error("Returning from loadAllPagesFromXml. Page Repository is null");
				this.pageRepository = null;
				return;
			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			logger.error("Unable to find the file. Path is incorrect");
			logger.error("Returning from loadAllPagesFromXml. Page Repository is null");
			this.pageRepository = null;
			return;
		}
		try {
			InputStream in = Config.class.getClassLoader().getResourceAsStream(fullPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Pages.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Pages que = (Pages) jaxbUnmarshaller.unmarshal(in);
			pages = que.getPages();
			if (pages.size() < 0) {
				logger.warn("Could not find any page defined in the xml file - {}", fullPath);
				logger.warn("<== Exiting loadAllPages method with null.");
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			logger.error("Uable to parse Pages XML file - {}", fullPath);
			logger.error("<== Exiting loadAllPages method with NULL");
		}
		logger.info("Pages from the xml are : [{}]", pages);
		this.pageRepo = pages;
		for (Page page : pages) {
			this.pageRepository.put(page.getName(), page);
			if (page.getIsHomePage()) {
				this.setCurrentPage(page.getName());
				this.setCurrentFrame(null);
			}
		}
		logger.info("Page Repo : [{}]", this.pageRepository);
		logger.info("<== Exiting loadAllPagesFromXml() method.");
	}

	public static void setInstanceToNull() {
		System.out.println("============> Setting instance to null");
		singletonInstance.dataMap = null;
		singletonInstance.pageRepo = null;
		singletonInstance.pageRepository = null;
		singletonInstance.environment = null;
		singletonInstance = null;
	}

	public static void main(String[] args) throws IOException, URISyntaxException {

		/*
		 * * Config instance = Config.getInstance(); Map<String, String> envMap =
		 * instance.dataMap.get(DataConstants.ENVIRONMENT_MAP); Map<String, String>
		 * locator = instance.dataMap.get("locatorLabelsMap");
		 * System.out.println(envMap); System.out.println(locator);
		 */

		/*
		 * File f = new File("Pages/ALKAMI_Pages.xml"); InputStream in =
		 * Config.class.getClassLoader() .getResourceAsStream("Pages/ALKAMI_Pages.xml");
		 * f = new
		 * File(Config.class.getClassLoader().getResource("Pages/ALKAMI_Pages.xml").
		 * toURI()); System.out.println(f.exists());
		 * System.out.println(Config.class.getClassLoader().getResource(
		 * "Pages/ALKAMI_Pages.xml") .toURI()); Config config =
		 * Config.createInstance("ALKAMI"); Page p = PageParser.getPage("SAML_LOGIN");
		 * System.out.println(p.getName()); System.out.println(p.getPreconditions());
		 * System.out.println(p.getPostconditions());
		 */
	}
}
