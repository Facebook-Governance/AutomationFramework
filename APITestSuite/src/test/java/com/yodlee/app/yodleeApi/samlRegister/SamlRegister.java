/*******************************************************************************
 *
 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.app.yodleeApi.samlRegister;

import static org.testng.Assert.assertNotNull;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.databene.benerator.anno.Source;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.app.yodleeApi.saml.SAMLConfigHelper;
import com.yodlee.app.yodleeApi.saml.Saml2OpenSamlHelper;
import com.yodlee.app.yodleeApi.saml.SamlHelper;
import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.SamlUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@Feature(featureName = { FeatureName.SAML })
public class SamlRegister extends Base {
	public static final String samlRegistration = "\\TestData\\CSVFiles\\Saml\\saml.csv";
	public static final String samlLogin = "\\TestData\\CSVFiles\\Saml\\samlLogin.csv";
	public static final String samlRegisterAndLoginTest = "\\TestData\\CSVFiles\\Saml\\samlRegisterAndLoginTest.csv";

	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	int count = 0;

	SamlUtils samlUtil = new SamlUtils();

	String userName = null;

	@BeforeClass
	public static void init() {

		System.out.println("Initializing Saml Configurations!");
		loadProperties("saml");
		
		
		
	}

	@Test(dataProvider = "feeder", enabled = true)
	@Info(subfeature = { SubFeature.SAML }, userStoryId = "")
	@Source(samlRegisterAndLoginTest)
	public void samlRegisterAndLoginTest(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		System.out.println("userStoryID:" + testcaseID);
		String userName = System.getProperty("userName") + System.currentTimeMillis();
		Response samlRegisterResponse = samlUserRegister(userName);
		String userSessionToken = getAttributeValue(samlRegisterResponse, "user.session.userSession");
		System.out.println("userSessionToken from samlRegister:" + userSessionToken);
		assertNotNull(userSessionToken, "Saml registeration Failed" + samlRegisterResponse);
		Response samlLoginResponse = samlUserLogin(userName);
		userSessionToken = getAttributeValue(samlLoginResponse, "user.session.userSession");
		System.out.println("userSessionToken from samlLogin:" + userSessionToken);
		assertNotNull(userSessionToken, "Saml Login Failed" + samlLoginResponse);
	}

	@Source(samlRegistration)
	@Test(dataProvider = "feeder", enabled = true, priority = 2)
	@Info(subfeature = { SubFeature.SAML }, userStoryId = "")
	public void testSamlRegister(String userStoryID, String testcaseName, String cobSessionKey, String cobSessionValue,
			String samlResponseKey, String samlResponseValue, String issuerKey, String issuerValue, String sourceKey,
			String sourceValue, int status, String responseFile, String responsePath, String enable, String defectID) {

		System.out.println("userStoryID:" + userStoryID);
		userName = System.getProperty("userName") + System.currentTimeMillis();
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams = headerParamsForSamlLogin(userName, samlResponseKey, samlResponseValue, issuerKey, issuerValue,
				sourceKey, sourceValue);
		System.out.println("Header:" + queryParams);

		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParams);
		Map<String, String> headerMap = new LinkedHashMap<>();
		headerMap.put("Authorization", headerWithEmptyCobSessionKey(cobSessionKey, cobSessionValue));
		httpMethodParams.setAddHeaders(headerMap);
		Response response = samlUtil.doSamlRegistration(Configuration.getInstance().getCobrandSessionObj(),
				httpMethodParams);

		System.out.println("userContextString:" + response.then().log().all());
		String userSessionToken = getAttributeValue(response, "user.session.userSession");
		System.out.println("USERTOKEN - " + userSessionToken);

		jsonAssertionUtil.assertResponse(response, status, responseFile, responsePath);
	}

	@Source(samlRegistration)
	@Test(dataProvider = "feeder", enabled = true, priority = 2)
	@Info(subfeature = { SubFeature.SAML }, userStoryId = "")
	public void testSamlRegisterNew(String userStoryID, String testcaseName, String cobSessionKey,
			String cobSessionValue, String samlResponseKey, String samlResponseValue, String issuerKey,
			String issuerValue, String sourceKey, String sourceValue, int status, String responseFile,
			String responsePath, String enable, String defectID) {

		System.out.println("userStoryID:" + userStoryID);
		userName = System.getProperty("userName") + System.currentTimeMillis();
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams = headerParamsForSamlLogin(userName, samlResponseKey, samlResponseValue, issuerKey, issuerValue,
				sourceKey, sourceValue);
		System.out.println("Header:" + queryParams);

		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParams);
		Map<String, String> headerMap = new LinkedHashMap<>();
		headerMap.put("Authorization", headerWithEmptyCobSessionKey(cobSessionKey, cobSessionValue));
		httpMethodParams.setAddHeaders(headerMap);
		Response response = samlUtil.doSamlRegistration(Configuration.getInstance().getCobrandSessionObj(),
				httpMethodParams);

		System.out.println("userContextString:" + response.then().log().all());
		String userSessionToken = getAttributeValue(response, "user.session.userSession");
		System.out.println("USERTOKEN - " + userSessionToken);

		jsonAssertionUtil.assertResponse(response, status, responseFile, responsePath);

	}

	@Source(samlLogin)
	@Test(dataProvider = "feeder", enabled = false, priority = 3)
	@Info(subfeature = { SubFeature.SAML }, userStoryId = "")
	public void testSamlLogin(String userStoryID, String testcaseName, String cobSessionKey, String cobSessionValue,
			String samlResponseKey, String samlResponseValue, String issuerKey, String issuerValue, String sourceKey,
			String sourceValue, int status, String responseFile, String responsePath, String enable, String defectID) {
		System.out.println("userStoryID:" + userStoryID);
		Map<String, String> queryParams = new HashMap<String, String>();

		queryParams = headerParamsForSamlLogin(userName, samlResponseKey, samlResponseValue, issuerKey, issuerValue,
				sourceKey, sourceValue);
		System.out.println("Header:" + queryParams);

		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParams);
		Map<String, String> headerMap = new LinkedHashMap<>();
		headerMap.put("Authorization", headerWithEmptyCobSessionKey(cobSessionKey, cobSessionValue));
		httpMethodParams.setAddHeaders(headerMap);
		Response response = samlUtil.doSamlRegistration(Configuration.getInstance().getCobrandSessionObj(),
				httpMethodParams);

		System.out.println("userContextString:" + response.then().log().all());
		String userSessionToken = getAttributeValue(response, "user.session.userSession");
		System.out.println("USERTOKEN - " + userSessionToken);

		jsonAssertionUtil.assertResponse(response, status, responseFile, responsePath);
	}

	public static void loadProperties(String propertyFileName) {
		ResourceBundle configBundle;
		Enumeration<String> enumerator;
		configBundle = ResourceBundle.getBundle(propertyFileName);
		enumerator = configBundle.getKeys();

		if (enumerator != null) {
			while (enumerator.hasMoreElements()) {
				String key = (String) enumerator.nextElement();
				System.setProperty(key, configBundle.getString(key));
			}
		}
	}

	public static String getSamlResponse(String username) {

		SamlHelper samlHelper = Saml2OpenSamlHelper.getSamlHelper(
				SAMLConfigHelper.getSingletonObject().getKeystoreFilename(),
				SAMLConfigHelper.getSingletonObject().getKeystoreAlias(),
				SAMLConfigHelper.getSingletonObject().getKeystorePassword());
		String samlResponseStr = null;
		try {
			samlResponseStr = samlHelper.generateResponseString(username,
					SAMLConfigHelper.getSingletonObject().getIssuer());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return samlResponseStr;
	}

	public static String headerWithEmptyCobSessionKey(String cobSessionKey, String cobSessionValue) {
		LinkedHashMap<String, String> hparams = new LinkedHashMap<>();
		if (!cobSessionKey.trim().equalsIgnoreCase("VALID")) {
			hparams.put(cobSessionKey, SessionHelper.getSessionToken(cobSessionValue, "cob"));
		} else if (!cobSessionValue.trim().equalsIgnoreCase("VALID")) {
			hparams.put("cobSession", SessionHelper.getSessionToken(cobSessionValue, "cob"));
		} else {
			hparams.put("cobSession", SessionHelper.getSessionToken(cobSessionValue, "cob"));
		}
		String headerParam = hparams.toString();
		System.out.println("The header is : " + headerParam);
		return headerParam;
	}

	public Response samlUserRegister(String username) {
		String samlResponseStr = getSamlResponse(username);

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams = headerParamsForSamlLogin(samlResponseStr);
		System.out.println("queryParams:" + queryParams);

		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParams);

		EnvSession envSession = EnvSession.builder().build();
		envSession.setCobSession(Configuration.getInstance().getCobrandSessionObj().getCobSession());
		envSession.setPath(Configuration.getInstance().getCobrandSessionObj().getPath());
		Response userContextString = samlUtil.doSamlRegistration(envSession, httpMethodParams);

		System.out.println("userContextString:" + userContextString.then().log().all());
		return userContextString;

	}

	public Response samlUserLogin(String username) {

		String samlResponseStr = getSamlResponse(username);

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams = headerParamsForSamlLogin(samlResponseStr);
		System.out.println("Header:" + queryParams);

		HttpMethodParameters httpMethodParams = HttpMethodParameters.builder().build();
		httpMethodParams.setQueryParams(queryParams);

		EnvSession envSession = EnvSession.builder().build();
		envSession.setCobSession(Configuration.getInstance().getCobrandSessionObj().getCobSession());
		envSession.setPath(Configuration.getInstance().getCobrandSessionObj().getPath());
		Response userContextString = samlUtil.doSamlLogin(envSession, httpMethodParams);

		System.out.println("userContextString user Login:" + userContextString.then().log().all());

		return userContextString;

	}

	private static Map<String, String> headerParamsForSamlLogin(String samlResponse) {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("samlResponse", samlResponse);
		headerMap.put("issuer", SAMLConfigHelper.getSingletonObject().getIssuer());
		headerMap.put("source", SAMLConfigHelper.getSingletonObject().getSamlSource());
		return headerMap;
	}

	public static String getAttributeValue(Response json, String attributeName) {
		String attributeValue = null;
		attributeValue = json.jsonPath().getString(attributeName);
		return attributeValue;
	}

	private static Map<String, String> headerParamsForSamlLogin(String userName, String samlResponseKey,
			String samlResponseValue, String issuerKey, String issuerValue, String sourceKey, String sourceValue) {
		Map<String, String> headerMap = new HashMap<String, String>();
		if (samlResponseKey.trim().equalsIgnoreCase("VALID") && samlResponseValue.trim().equalsIgnoreCase("VALID")) {
			String samlResponseStr = getSamlResponse(userName);
			headerMap.put("samlResponse", samlResponseStr);
		} else if (samlResponseKey.trim().equalsIgnoreCase("VALID")
				&& !samlResponseValue.trim().equalsIgnoreCase("VALID")) {
			headerMap.put("samlResponse", samlResponseValue);
		} else if (samlResponseKey.trim().equalsIgnoreCase("INVALID")
				&& samlResponseValue.trim().equalsIgnoreCase("VALID")) {
			String samlResponseStr = getSamlResponse(userName);
			headerMap.put(samlResponseKey, samlResponseStr);
		} else {
			headerMap.put(samlResponseKey, samlResponseValue);
		}

		if (issuerKey.trim().equalsIgnoreCase("VALID") && issuerValue.trim().equalsIgnoreCase("VALID")) {
			headerMap.put("issuer", SAMLConfigHelper.getSingletonObject().getIssuer());
		} else if (issuerKey.trim().equalsIgnoreCase("VALID") && !issuerValue.trim().equalsIgnoreCase("VALID")) {
			headerMap.put("issuer", issuerValue);
		} else if (issuerKey.trim().equalsIgnoreCase("INVALID") && issuerValue.trim().equalsIgnoreCase("VALID")) {
			headerMap.put(issuerKey, SAMLConfigHelper.getSingletonObject().getIssuer());
		} else {
			headerMap.put(issuerKey, issuerValue);
		}

		if (sourceKey.trim().equalsIgnoreCase("VALID") && sourceValue.trim().equalsIgnoreCase("VALID")) {
			headerMap.put("source", SAMLConfigHelper.getSingletonObject().getSamlSource());
		} else if (sourceKey.trim().equalsIgnoreCase("VALID") && !sourceValue.trim().equalsIgnoreCase("VALID")) {
			headerMap.put("source", sourceValue);
		} else if (sourceKey.trim().equalsIgnoreCase("INVALID") && sourceValue.trim().equalsIgnoreCase("VALID")) {
			headerMap.put(sourceKey, SAMLConfigHelper.getSingletonObject().getSamlSource());
		} else {
			headerMap.put(sourceKey, sourceValue);
		}
		return headerMap;
	}
}
