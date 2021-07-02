/*******************************************************************************
 *
 * * Copyright (c) 201 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.samlRegister;

import java.util.HashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.testng.annotations.Test;

import com.yodlee.app.yodleeApi.saml.SamlHelper_old;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;

import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;


import io.restassured.response.Response;

public class TestSamlAuthNonSDG extends Base {

	public static final String samlAuthentication = "\\TestData\\CSVFiles\\Saml\\samlAuth.csv";

	
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	SamlHelper_old samlHelper = new SamlHelper_old();
	
	
	
	Configuration configurationObj = Configuration.getInstance();
	UserUtils userUtils = new UserUtils();

	@Source(samlAuthentication)
	@Test(enabled = true, dataProvider = "feeder")
	public void testSamlAuthenticationNonSDG(String TestCaseID, String ISSUER_NAME, String IssuerKey,
			String SAML_LOGIN_ISSUER, String SourceKey, String SAML_LOGIN_SOURCE, String subjectUser,
			String SAML_VERSION, String ASSERTION_ENCRYPTION, String ATTRIBUTE_ENCRYPTION,
			String CUSTOM_ATTRIBUTE_ENCRYPTION, String ATTRIBUTE_ENCODING, String ATTRIBUTE_ENCODING_SCHEME,
			String SIGN_RESPONSE, String SIGN_ASSERTION, String MULTIPLE_KEYS, String ATTRIBUTE_ENCRYPTION_MECHANISM,
			String SIGNING_ALIAS_KEY, String SIGNING_ATTRIBUTE_KEY, String YodleeAttributeSAMLReg,
			String YodleeAttribute, String CobSessionKey, String samlResponseKey, String samlResponseValue,
			String status, String responseFile, String responseFilePath, String enable) {
		System.out.println("testSamlRegister login");
		String userName = "YSLSaml" + System.currentTimeMillis();

		String samlResponseForUserLogin = samlHelper.getSamlConfig(userName, ISSUER_NAME, ATTRIBUTE_ENCRYPTION,
				ASSERTION_ENCRYPTION, MULTIPLE_KEYS, SIGN_RESPONSE, SIGN_ASSERTION, YodleeAttributeSAMLReg, "false",
				SIGNING_ATTRIBUTE_KEY, SIGNING_ALIAS_KEY, SAML_VERSION, ATTRIBUTE_ENCODING, CUSTOM_ATTRIBUTE_ENCRYPTION,
				ATTRIBUTE_ENCRYPTION_MECHANISM, ATTRIBUTE_ENCODING_SCHEME);
		System.out.println("Test Case ID :" + TestCaseID);
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put(samlResponseKey, samlResponseForUserLogin);

		
		if(ISSUER_NAME.trim().equalsIgnoreCase("VALID")) {
			ISSUER_NAME=configurationObj.getSamlIssuer();
		};
		if(SAML_LOGIN_ISSUER.trim().equalsIgnoreCase("VALID")) {
			SAML_LOGIN_ISSUER=configurationObj.getSamlIssuer();
		};
		
		if(SAML_LOGIN_SOURCE.trim().equalsIgnoreCase("VALID")) {
			SAML_LOGIN_SOURCE=configurationObj.getSamlSource();
		};
		
		
		queryParams.put(IssuerKey, SAML_LOGIN_ISSUER);
		queryParams.put(SourceKey, SAML_LOGIN_SOURCE);
		queryParams.put("cobSessionToken", configurationObj.getCobrandSessionObj().getCobSession());
		String cobSessionKey = null; 
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParams);

		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.cobSessionKey(cobSessionKey).path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response responseSamlLogin = userUtils.doSamlLogin(httpParams, session);
		String userSessionToken = samlHelper.getAttributeValue(responseSamlLogin, "user.session.userSession");

		if (!responseFile.trim().equalsIgnoreCase("")) {

			jsonAssertionUtil.assertResponse(responseSamlLogin, responseSamlLogin.statusCode(), responseFile,
					responseFilePath);
		} else {
			org.testng.Assert.assertNotNull(userSessionToken);
		}
	}

	
}