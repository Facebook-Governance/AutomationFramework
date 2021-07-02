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

package com.yodlee.app.yodleeApi.schemaValidation;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.databene.benerator.anno.Source;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.RestApi.RestApiUtils.UserUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.Initializer;
import com.yodlee.yodleeApi.common.NonSdgLoginForm;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.CobrandHelper;
import com.yodlee.yodleeApi.helper.DerivedHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.StatementsHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.Cobrand;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.schemaValidation.AccountStatus;
import com.yodlee.yodleeApi.schemaValidation.AccountType;
import com.yodlee.yodleeApi.schemaValidation.AuthType;
import com.yodlee.yodleeApi.schemaValidation.CurrencyValidator;
import com.yodlee.yodleeApi.schemaValidation.DateOnlyValidator;
import com.yodlee.yodleeApi.schemaValidation.DateTimeValidator;
import com.yodlee.yodleeApi.schemaValidation.EnumContainerTypes;
import com.yodlee.yodleeApi.schemaValidation.EnumEventName;
import com.yodlee.yodleeApi.schemaValidation.EnumLocale;
import com.yodlee.yodleeApi.schemaValidation.EnumMerchantSource;
import com.yodlee.yodleeApi.schemaValidation.EnumProviderAccountAggSource;
import com.yodlee.yodleeApi.schemaValidation.EnumProviderAccountDetailsStatus;
import com.yodlee.yodleeApi.schemaValidation.EnumProviderAccountStatus;
import com.yodlee.yodleeApi.schemaValidation.EnumRefreshStatus;
import com.yodlee.yodleeApi.schemaValidation.EnumStatusCode;
import com.yodlee.yodleeApi.schemaValidation.EnumTransactionBaseTypes;
import com.yodlee.yodleeApi.schemaValidation.EnumTransactionStatus;
import com.yodlee.yodleeApi.schemaValidation.EnumTransactionTypes;
import com.yodlee.yodleeApi.schemaValidation.LifeInsuranceType;
import com.yodlee.yodleeApi.schemaValidation.MFAType;
import com.yodlee.yodleeApi.schemaValidation.ProviderStatus;
import com.yodlee.yodleeApi.schemaValidation.enumContainerTypes;
import com.yodlee.yodleeApi.schemaValidation.enumTransactionTypes;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.DerivedUtils;
import com.yodlee.yodleeApi.utils.apiUtils.NotificationUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.apiUtils.StatementUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

//import com.yodlee.app.yodleeApi.SecurityEnhancements.test.UserAndCobrandHelper;

import io.restassured.response.Response;

//import static com.yodlee.app.yodleeApi.samlRegister.SamlRegister.loadProperties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author msusainathan
 */
public class testSchema extends Base {

	String samlUserName = "userName" + System.currentTimeMillis();
	String tokenValue = "";
	String appIdValid = "10003600";
	String userName = "SchemaUser" + System.currentTimeMillis();
	String password = "Password#123";
	public String dagUN = "YSLSchemaUser.site16441.1";
	public String dagPwd = "site16441.1";
	public AccountsHelper accountsHelper = new AccountsHelper();
	// public UserAndCobrandHelper userAndCobrandHelper = new
	// UserAndCobrandHelper();
	public static Long providerAccountID;
	long providerId = 16441;
	String userSession = null;
	EnvSession envSession;
	String loginName = "schema" + System.currentTimeMillis();
	CommonUtils commonUtils = new CommonUtils();
	Configuration configObj = Configuration.getInstance();
	UserUtils userutil = new UserUtils();
	StatementUtils statementUtil = new StatementUtils();

	TransactionUtils transactionutil = new TransactionUtils();

	ProviderAccountUtils providerAccountUtil = new ProviderAccountUtils();
	DerivedHelper derivedHelper = new DerivedHelper();
	DerivedUtils derivedUtil = new DerivedUtils();

	ProvidersHelper providerHelper = new ProvidersHelper();
	ProviderUtils providerUtils = new ProviderUtils();

	@BeforeClass(alwaysRun = true)
	public void setUpTest() {
		// loadProperties("saml");
		logger.info("********* EXECUTING BEFORE CLASS*****************");
		// userAndCobrandHelper = new UserAndCobrandHelper();
		UserUtils userUtil = new UserUtils();
		UserHelper userHelper = new UserHelper();

		envSession = EnvSession.builder().cobSession(configObj.getCobrandSessionObj().getCobSession()).path(configObj.getCobrandSessionObj().getPath()).build();
		
		
		User userInfo = User.builder().build();
		userInfo.setUsername("YSLDE" + System.currentTimeMillis());
		userInfo.setPassword("TEST@123");
		userInfo.setEmailId("abc@yodlee.com");

		HttpMethodParameters httpParams = userHelper.createInputForUserReg(userInfo);
		Response response = (Response) userUtil.userRegistrationResponse(envSession, httpParams);

		response.then().log().all();
		if (response.statusCode() == 200) {
			userName = response.jsonPath().getString("user.loginName");
			userSession = response.jsonPath().getString("user.session.userSession");
			logger.info("userSession:" + userSession);
		}

		envSession.setUserSession(userSession);
		ProviderAccountUtils paccUtil = new ProviderAccountUtils();
		Response getProviderAccountResponse = paccUtil.addProviderAccountStrict(providerId, "simplified", dagUN, dagPwd,
				envSession);

		providerAccountID = getProviderAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);

		logger.info("Provider Account Id : " + providerAccountID);
		response.then().log().all();

	}

	@SuppressWarnings({ "deprecation", "deprecation", "deprecation" })
	@Source("\\TestData\\CSVFiles\\validateSchemaCSV\\schema.csv")
	@Test(dataProvider = "feeder", enabled = true, priority = 1)
	public void verifySchema(String testcaseId, String entity, String container, String schemaPath, String URI,
			String method, String enabled) throws IOException {
		System.out.println("Method:" + method);
		JSONObject jsonSubject = null;

		commonUtils.isTCEnabled(testcaseId, enabled);
		if (entity.trim().equalsIgnoreCase("cobrand")) {
			if (method.trim().equalsIgnoreCase("Cobrand Login")) {

				HttpMethodParameters httpParms = HttpMethodParameters.builder().build();
				CobrandHelper cobrandhelper = new CobrandHelper();

				httpParms = cobrandhelper.createInputForCobrandLogin(configObj.getCobrandObj());

				Response responsecobSession = (Response) configObj.getFirstLevelAuthorizationObj().getLoginResponse(
						httpParms, configObj.getYslBasePath() + configObj.getCobrandObj().getCobrandName());
				jsonSubject = new JSONObject(new JSONTokener(responsecobSession.asString()));

			} else if (method.trim().equalsIgnoreCase("GetSubscribedEvents")) {

				HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
				Map<String, String> queryParams = new HashMap<String, String>();
				queryParams.put("eventName", "REFESH");
				httpParams.setQueryParams(queryParams);

				NotificationUtils util = new NotificationUtils();
				Response response = util.getSubscribedevents(httpParams, envSession);
				// jsonSubject = new JSONObject(new
				// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/getSubscribedEvent.json")));
				jsonSubject = new JSONObject(new JSONTokener(response.asString()));
			} else if (method.trim().equalsIgnoreCase("GetPublicKey")) {
				HttpMethodParameters httpParms = HttpMethodParameters.builder().build();
				CobrandHelper cobrandhelper = new CobrandHelper();

				httpParms = cobrandhelper.createInputForCobrandLogin(configObj.getCobrandObj());
				Response responseCobSessionwithHeader = (Response) configObj.getFirstLevelAuthorizationObj()
						.getLoginResponse(httpParms, configObj.getYslBasePath() + URI);
				/*
				 * Response response =
				 * RestUtil.getWithCobSessionAsHeader(InitialLoader.
				 * cobSessionObj.getCobSession(),
				 * InitialLoader.cobSessionObj.getPath() + URI);
				 */
				// jsonSubject = new JSONObject(new
				// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/getSubscribedEvent.json")));
				jsonSubject = new JSONObject(new JSONTokener(responseCobSessionwithHeader.asString()));
			}
		}

		UserHelper user = new UserHelper();
		User userInfo = User.builder().build();
		userInfo.setUsername(loginName);
		userInfo.setPassword("Password#123");
		HttpMethodParameters methodParams = user.createInputForUserReg(userInfo);

		envSession.setPath(configObj.getCobrandSessionObj().getPath());
		envSession.setUserSession(userSession);
		envSession.setCobSession(configObj.getCobrandSessionObj().getCobSession());
		if (entity.trim().equalsIgnoreCase("user")) {
			if (method.trim().equalsIgnoreCase("RegisterUser")) {

				Response response = (Response) userutil.userRegistrationResponse(envSession, methodParams);

				// jsonSubject = new JSONObject(new
				// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/userRegister.json")));
				jsonSubject = new JSONObject(new JSONTokener(response.asString()));
			}
			if (method.trim().equalsIgnoreCase("UserLogin")) {
				//
				envSession.setPath(configObj.getCobrandSessionObj().getPath() + URI);
				Response response = userutil.updateUserDetails(methodParams, envSession);
				response.then().log().all();
				String userSession = response.jsonPath().getString(JSONPaths.USER_SESSION_TOKEN);
				// jsonSubject = new JSONObject(new
				// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/userRegister.json")));
				jsonSubject = new JSONObject(new JSONTokener(response.asString()));
			}

			if (method.trim().equalsIgnoreCase("GetAccessTokens")) {

				Map<String, Object> params = user.createQueryParamsGetAccessToken(appIdValid);
				HttpMethodParameters methodParams2 = HttpMethodParameters.builder().build();
				methodParams2.setQueryParams(params);
				Response response = userutil.getAccessTokens(methodParams2, envSession);
				jsonSubject = new JSONObject(new JSONTokener(response.asString()));
			}

			if (method.trim().equalsIgnoreCase("GetToken")) {

				Map<String, Object> queryParam = new HashMap<>();
				queryParam.put("loginName", loginName);
				HttpMethodParameters methodParams3 = HttpMethodParameters.builder().build();
				methodParams3.setQueryParams(queryParam);
				Response getTokenResponse = userutil.getUserCredentialToken(methodParams3, envSession);
				tokenValue = getTokenResponse.jsonPath().getString("token");
				System.out.println("tokenValue:" + tokenValue);
				jsonSubject = new JSONObject(new JSONTokener(getTokenResponse.asString()));
			}
			if (method.trim().equalsIgnoreCase("SamlRegister")) {
				// Response response = samlUserRegister(samlUserName);
				jsonSubject = new JSONObject(new JSONTokener(
						testSchema.class.getResourceAsStream("/TestData/schemaValidation/samlRegister.json")));
				// jsonSubject = new JSONObject(new
				// JSONTokener(response.asString()));

			}
			if (method.trim().equalsIgnoreCase("SamlLogin")) {
				// Response response= samlUserLogin(samlUserName);
				jsonSubject = new JSONObject(new JSONTokener(
						testSchema.class.getResourceAsStream("/TestData/schemaValidation/samlRegister.json")));
				// jsonSubject = new JSONObject(new
				// JSONTokener(response.asString()));
			}

			if (method.trim().equalsIgnoreCase("GetUserDetails")) {

				Response response = userutil.updateUserDetails(null, envSession);

				jsonSubject = new JSONObject(new JSONTokener(response.asString()));
			}
		}

		if (method.equalsIgnoreCase("statements")) {
			LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("container", container);
			HttpMethodParameters methodParams4 = HttpMethodParameters.builder().build();
			methodParams4.setQueryParams(params);

			statementUtil.getStatements(methodParams4, envSession);
			Response statementResponse = statementUtil.getStatements(methodParams4, envSession);

			statementResponse.then().log().all();
			System.out.println("statement json:" + statementResponse.asString());
			jsonSubject = new JSONObject(new JSONTokener(statementResponse.asString()));
		} else if (method.equalsIgnoreCase("getTransaction")) {

			HttpMethodParameters methodParams5 = HttpMethodParameters.builder().build();
			LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
			params.put("container", container);
			params.put("fromDate", "2010-09-09");
			methodParams.setQueryParams(params);

			Response transactionResponse = transactionutil.getAllTransactions(methodParams5, envSession);

			transactionResponse.then().log().all();
			System.out.println("transaction response:" + transactionResponse.asString());
			jsonSubject = new JSONObject(new JSONTokener(transactionResponse.asString()));
		} else if (method.equalsIgnoreCase("getProviderAccounts")) {

			Response providerAccountResponse = providerAccountUtil.getAllProviderAccounts(envSession);

			providerAccountResponse.then().log().all();
			jsonSubject = new JSONObject(new JSONTokener(providerAccountResponse.asString()));

		} else if (method.equalsIgnoreCase("getProviderAccountDetails")) {

			HashMap<String, Object> pathParams = new HashMap<String, Object>();
			pathParams.put("providerAccountID", providerAccountID);
			HttpMethodParameters methodParams0 = HttpMethodParameters.builder().build();
			methodParams0.setPathParams(pathParams);

			Response providerAccountDetailResponse = providerAccountUtil.getProviderAcctDetails(methodParams0,
					envSession);

			providerAccountDetailResponse.then().log().all();

			jsonSubject = new JSONObject(new JSONTokener(providerAccountDetailResponse.asString()));
		} else if (entity.trim().equalsIgnoreCase("derived")) {
			if (method.trim().equalsIgnoreCase("networthSummary")) {
				HttpMethodParameters methodParams6 = HttpMethodParameters.builder().build();

				derivedHelper.createQueryParamsForNetworthSummary(null, null, null, null, null, null, null, null);

				envSession.setPath(configObj.getCobrandSessionObj().getPath());
				Response networthSummaryResponse = derivedUtil.getDerivedNetworthSummary(methodParams6, envSession);

				networthSummaryResponse.then().log().all();

				System.out.println("Response:" + networthSummaryResponse.asString());
				// jsonSubject = new JSONObject(new
				// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/networth_summary.json")));
				jsonSubject = new JSONObject(new JSONTokener(networthSummaryResponse.asString()));
			}
		} else if (entity.trim().equalsIgnoreCase("historicalBalances")) {
			if (method.trim().equalsIgnoreCase("historicalBalances")) {

				AccountsHelper accountHelper = new AccountsHelper();
				HttpMethodParameters methodParams7 = HttpMethodParameters.builder().build();
				methodParams7.setPathParams(accountHelper.createQueryParamsForGetHistoricalBalance(null, null, null,
						null, null, null, null, null));
				AccountUtils accountUtils = new AccountUtils();

				Response historicalBalancesResponse = accountUtils.getHistoricalBalance(methodParams7, envSession);
				historicalBalancesResponse.then().log().all();

				System.out.println("Response:" + historicalBalancesResponse.asString());
				// jsonSubject = new JSONObject(new
				// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/historicalBalances.json")));
				jsonSubject = new JSONObject(new JSONTokener(historicalBalancesResponse.asString()));
			}
		} else if (entity.trim().equalsIgnoreCase("providers")) {
			if (method.trim().equalsIgnoreCase("getProviders")) {

				HttpMethodParameters methodParams8 = HttpMethodParameters.builder().build();
				methodParams8.setQueryParams(
						providerHelper.createQueryParamsForGetProviders(null, null, null, null, null));
				Map<String, String> queryParams = new LinkedHashMap<>();

				Response providersResponse = providerUtils.getProviders(methodParams8, envSession);

				System.out.println("Response:" + providersResponse.asString());
				// jsonSubject = new JSONObject(new
				// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/getProviders.json")));
				jsonSubject = new JSONObject(new JSONTokener(providersResponse.asString()));
			}

			else if (method.trim().equalsIgnoreCase("getProviderDetails")) {

				Map<String, Object> pathParams = new LinkedHashMap<>();
				pathParams.put("providerId", "16441");
				HttpMethodParameters methodParams9 = HttpMethodParameters.builder().build();
				methodParams9.setPathParams(pathParams);

				Response providerDetailsResponse = providerUtils.getProviderDetails(methodParams9, envSession);
				System.out.println("Response:" + providerDetailsResponse.asString());
				// jsonSubject = new JSONObject(new
				// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/getProviders.json")));
				jsonSubject = new JSONObject(new JSONTokener(providerDetailsResponse.asString()));
			}
		}

		else if (method.trim().equalsIgnoreCase("getAccounts")) {

			AccountsHelper accountHelper = new AccountsHelper();

			HttpMethodParameters methodParameters9 = HttpMethodParameters.builder().build();
			methodParameters9.setQueryParams(
					accountHelper.createQueryParamsForGetAccounts(null, container, null, null, null, null, null));
			AccountUtils accountUtil = new AccountUtils();

			Response getAccountSummaryResponse = accountUtil.getAccounts(methodParameters9, envSession);
			System.out.println("Response:" + getAccountSummaryResponse.asString());
			// jsonSubject = new JSONObject(new
			// JSONTokener(testSchema.class.getResourceAsStream("/TestData/schemaValidation/getProviders.json")));
			jsonSubject = new JSONObject(new JSONTokener(getAccountSummaryResponse.asString()));

		}

		JSONObject jsonSchema = new JSONObject(new JSONTokener(testSchema.class.getResourceAsStream(schemaPath)));
		
		SchemaLoader schemaLoader = SchemaLoader.builder().schemaJson(jsonSchema)
				.addFormatValidator("money", new CurrencyValidator())// rawSchema
																		// is
																		// the
																		// JSON
																		// representation
																		// of
																		// the
																		// schema
																		// utilizing
																		// the
																		// "evenlength"
																		// non-standard
																		// format
				.addFormatValidator("datetime", new DateTimeValidator()).addFormatValidator("locale", new EnumLocale())
				.addFormatValidator("eventName", new EnumEventName())
				.addFormatValidator("date", new DateOnlyValidator())
				.addFormatValidator("container", new EnumContainerTypes())
				.addFormatValidator("baseType", new EnumTransactionBaseTypes())
				.addFormatValidator("transactionStatus", new EnumTransactionStatus())
				.addFormatValidator("transactionType", new EnumTransactionTypes())
				.addFormatValidator("merchantSource", new EnumMerchantSource())
				.addFormatValidator("aggSource", new EnumProviderAccountAggSource())
				.addFormatValidator("providerAccStatus", new EnumProviderAccountStatus())
				.addFormatValidator("providerAccDetailsStatus", new EnumProviderAccountDetailsStatus())
				.addFormatValidator("statusCode", new EnumStatusCode())
				.addFormatValidator("refreshStatus", new EnumRefreshStatus())
				.addFormatValidator("providerStatus", new ProviderStatus()).addFormatValidator("MFAType", new MFAType())
				.addFormatValidator("AuthType", new AuthType())
				.addFormatValidator("lifeInsuranceType", new LifeInsuranceType())
				.addFormatValidator("accountStatus", new AccountStatus())
				.addFormatValidator("accountType", new AccountType()).build();

		try {
			Schema schema = schemaLoader.load().build();
			schema.validate(jsonSubject);
		} catch (ValidationException e) {

			Assert.fail("Error:" + e.getMessage() + "::" + e.toString() + "LL::" + e.getCausingExceptions());
		} catch (Exception e) {
			Assert.fail("Error:" + e.getMessage() + "::" + e.toString());
			e.printStackTrace();
		}

	}

	@AfterClass
	public void unRegister() {
		// ApiUtils.unRegisterUser(obj, userSession);

	}
}
