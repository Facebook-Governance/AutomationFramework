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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.NonSdgLoginForm;
import com.yodlee.yodleeApi.constants.AccountStatus;
import com.yodlee.yodleeApi.constants.*;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.EncryptionUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * Helper class for Provider Account related API's
 * 
 * @author Soujanya Kokala & Rahul Kumar
 *
 */
public class ProviderAccountsHelper {

	public String providerAccountUrl;

	public ProviderAccountsHelper() {
		this.providerAccountUrl = URLConstants.PROVIDER_ACCOUNTS_WRITE_FRAMEWORK;
	}

	public ProviderAccountsHelper(String providerAccountsUrl) {
		this.providerAccountUrl = providerAccountsUrl;
	}

	/**
	 * This method is used to fetch the provider account status from the response
	 * provided.
	 * 
	 * @param providerAccountDetailsResponse
	 *            it is a response object
	 * @return
	 */
	public String getProviderAccountDetailsStatus(Response providerAccountDetailsResponse) {

		String status = AccountStatus.SUCCESS;
		providerAccountDetailsResponse.then().log().all();
		if (providerAccountDetailsResponse.statusCode() == HttpStatus.OK) {
			if (providerAccountDetailsResponse.jsonPath().get(JSONPaths.REFRESH_INFO_STATUS).toString()
					.startsWith("[")) {
				status = providerAccountDetailsResponse.jsonPath().get(JSONPaths.REFRESH_INFO_STATUS)
						.toString().replaceAll("\\[|\\]", "");
			} else {
				status = providerAccountDetailsResponse.jsonPath().getString(JSONPaths.REFRESH_INFO_STATUS);
			}

		} else {
			status = AccountStatus.FAILED;
			System.out.println("Refresh status is : " + status);
		}
		return status;
	}

	/**
	 * This method is used to fetch the provider account id from the response
	 * passed.
	 * 
	 * @param addAccountResponse
	 *            it is a response object
	 * @return
	 */
	public Long getProviderAccountId(Response addAccountResponse) {
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		Long providerAccountId = null;
		if (providerAccountStatusCode == HttpStatus.CREATED) {
			if (addAccountResponse.jsonPath().get("providerAccount.id").toString().startsWith("[")) {
				providerAccountId = Long.valueOf(
						(addAccountResponse.jsonPath().get("providerAccount.id").toString().replaceAll("\\[|\\]", "")));
			} else {
				providerAccountId = addAccountResponse.jsonPath().getLong("providerAccount.id");
			}
			System.out.println("Site account id is : " + providerAccountId);

		}
		return providerAccountId;
	}

	private String setAndGetTokenForMfa(JSONObject addAccountMfaObject, String tokenValue) throws JSONException {
		JSONObject loginFormTokenObject = addAccountMfaObject.getJSONObject(Constants.LOGIN_FORM);
		loginFormTokenObject.getJSONArray(Constants.ROW).getJSONObject(0).getJSONArray(Constants.FIELD).getJSONObject(0)
				.remove(Constants.VALUE);
		loginFormTokenObject.getJSONArray(Constants.ROW).getJSONObject(0).getJSONArray(Constants.FIELD).getJSONObject(0)
				.put(Constants.VALUE, tokenValue);

		return new JSONObject().put("loginForm", loginFormTokenObject).toString();

	}

	private String setAndGetQandA(JSONObject updateMfaObject, String answer1, String answer2) throws JSONException {

		JSONObject qandAObject = updateMfaObject.getJSONObject(Constants.LOGIN_FORM);
		JSONArray rowArray = qandAObject.getJSONArray(Constants.ROW);
		for (int i = 0; i < rowArray.length(); i++) {
			if (rowArray.getJSONObject(i).getJSONArray(Constants.FIELD).getJSONObject(0).getString("name").toLowerCase()
					.equals("question_1")) {
				qandAObject.getJSONArray(Constants.ROW).getJSONObject(i).getJSONArray(Constants.FIELD).getJSONObject(0)
						.remove(Constants.VALUE);
				qandAObject.getJSONArray(Constants.ROW).getJSONObject(i).getJSONArray(Constants.FIELD).getJSONObject(0)
						.put(Constants.VALUE, answer1);

			} else if (rowArray.getJSONObject(i).getJSONArray(Constants.FIELD).getJSONObject(0).getString("name")
					.toLowerCase().equals("question_2")) {
				qandAObject.getJSONArray(Constants.ROW).getJSONObject(i).getJSONArray(Constants.FIELD).getJSONObject(0)
						.remove(Constants.FIELD);
				qandAObject.getJSONArray(Constants.ROW).getJSONObject(i).getJSONArray(Constants.FIELD).getJSONObject(0)
						.put(Constants.VALUE, answer2);
			}
		}
		return new JSONObject().put(Constants.LOGIN_FORM, qandAObject).toString();

	}

	/**
	 * This method is used to create loginForm object
	 * 
	 * @param loginForm
	 *            Creates a JSONObject - key is Constants.LOGIN_FORM and the value
	 *            is login form that is passed
	 * @return JSONObject
	 * @throws JSONException
	 */
	public JSONObject createLoginFormObject(JSONObject loginForm) throws JSONException {

		JSONObject loginFormObject = new JSONObject();
		loginFormObject.put(Constants.LOGIN_FORM, loginForm);

		return loginFormObject;
	}

	/**
	 * This method is used create login form input for MFA security QA for both SDG
	 * and NON-SDG.
	 * 
	 * @param firstQues
	 *            it is first question asked
	 * @param firstAns
	 *            it is an answer for first question
	 * @param secondQues
	 *            it is second question asked
	 * @param secondAns
	 *            it is an answer for second question
	 * @return
	 * 
	 * 		{"loginForm":{"mfaTimeout": 90900,"formType":
	 *         "questionAndAnswer","row":[ {"id":
	 *         "SQandA--QUESTION_1--Row--1","fieldRowChoice": "0001","form":
	 *         "0001","label": + firstQues, "field": [{"id":
	 *         "SQandA--QUESTION_1--1","name": "QUESTION_1","isOptional":
	 *         false,"value": + firstAns,"valueEditable": "true","type": "text"}]},
	 *         {"id": "SQandA--QUESTION_2--Row--2","fieldRowChoice": "0002","form":
	 *         "0001","label": + secondQues, "field": [{"id":
	 *         "SQandA--QUESTION_2--2","name": "QUESTION_2","isOptional":
	 *         false,"value": + secondAns,"valueEditable": "true","type":
	 *         "text"}]}]}}
	 * 
	 */
	public String createInputForMFASecurityQA(String firstQues, String firstAns, String secondQues, String secondAns) {

		JSONObject loginFormObject = new JSONObject();
		JSONObject loginForm = new JSONObject();

		loginForm.put("mfaTimeout", "90900");
		loginForm.put("formType", "questionAndAnswer");

		JSONArray rowArray = new JSONArray();

		JSONObject row1 = new JSONObject();
		row1.put("id", "SQandA--QUESTION_1--Row--1");
		row1.put("fieldRowChoice", "0001");
		row1.put("form", "0001");
		row1.put("label", firstQues);

		JSONArray fieldArray1 = new JSONArray();
		JSONObject fieldObject1 = new JSONObject();
		fieldObject1.put("id", "SQandA--QUESTION_1--1");
		fieldObject1.put("name", "QUESTION_1");
		fieldObject1.put("isOptional", false);
		fieldObject1.put("value", firstAns);
		fieldObject1.put("valueEditable", "true");
		fieldObject1.put("type", "text");

		fieldArray1.put(fieldObject1);
		row1.put("field", fieldArray1);
		rowArray.put(row1);

		JSONObject row2 = new JSONObject();
		row2.put("id", "SQandA--QUESTION_2--Row--2");
		row2.put("fieldRowChoice", "0002");
		row2.put("form", "0001");
		row2.put("label", secondQues);

		JSONArray fieldArray2 = new JSONArray();
		JSONObject fieldObject2 = new JSONObject();
		fieldObject2.put("id", "SQandA--QUESTION_2--2");
		fieldObject2.put("name", "QUESTION_2");
		fieldObject2.put("isOptional", false);
		fieldObject2.put("value", secondAns);
		fieldObject2.put("valueEditable", "true");
		fieldObject2.put("type", "text");

		fieldArray2.put(fieldObject2);
		row2.put("field", fieldArray2);
		rowArray.put(row2);

		loginForm.put("row", rowArray);

		loginFormObject.put("loginForm", loginForm);
		System.out.println("The MFA SecurityQA Loginform is: \n" + loginFormObject.toString());

		return loginFormObject.toString();
	}

	/**
	 * This method is used directly to create HttpMethods params for
	 * createInputforMFAChallengeQandA().
	 * 
	 * @param providerAccountId
	 *            it is provide account id
	 * @param firstQues
	 *            it is first question asked
	 * @param firstAns
	 *            it is an answer for first question
	 * @param secondQues
	 *            it is second question asked
	 * @param secondAns
	 *            it is an answer for second question
	 * @return
	 */
	public HttpMethodParameters createInputforMFAChallengeQandA(Long providerAccountId, String firstQues,
			String firstAns, String secondQues, String secondAns) {
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put(Constants.MULTIPLE_ACCOUNT_IDS_FILTER, String.valueOf(providerAccountId));

		String jsonFormed = createInputForMFASecurityQA(firstQues, firstAns, secondQues, secondAns);
		HttpMethodParameters httpParms = HttpMethodParameters.builder().build();
		httpParms.setQueryParams(queryParams);
		httpParms.setBodyParams(jsonFormed);
		return httpParms;
	}
	
	 /**
		 * JSON for NEGATIVE Scenarios.
		 * 
		 * { "providerAccount": { "id": 443434, "account": [{ "accountNumber":
		 * 232432, "accountType": "SAVINGS", "verification": { "type": "MATCHING" }
		 * }] } }
		 * 
		 * @param accountNumberKey
		 * @param accountNumberValue
		 * @param accountNameKey
		 * @param accountNameValue
		 * @param accountTypeKey
		 * @param accountTypeValue
		 * @param bankTransferIdKey
		 * @param bankTransferIdValue
		 * @param bankTransferTypeKey
		 * @param bankTransferTypeValue
		 * @param verificationTypeKey
		 * @param verificationTypeValue
		 * @return
		 * @throws JSONException
		 */
		public String createJsonForMatchingService(
				String providerAccountIdKey, String providerAccountId,
				String accountNumberKey, String accountNumber,
				String accountTypeKey, String accountType,
				String verificationTypeKey, String verificationType)
				throws JSONException {

			JSONObject providerAccountsObject = new JSONObject();
			JSONObject providerAccounts = new JSONObject();

			JSONArray accountArray = new JSONArray();
			JSONObject account = new JSONObject();
			account.put(accountNumberKey, accountNumber);
			account.put(accountTypeKey, accountType);

			JSONObject verification = new JSONObject();
			verification.put("type", verificationType);

			account.put("verification", verification);
			accountArray.put(account);

			providerAccounts.put("account", accountArray);
			providerAccounts.put(providerAccountIdKey, providerAccountId);
			providerAccountsObject.put("providerAccount", providerAccounts);
			System.out.println("BODY PARAM FOR CDV INITIATION : \n\n\n"
					+ providerAccountsObject.toString(10));
			return providerAccountsObject.toString();
		}
		
		/**
		 * JSON for Positive Scenarios.
		 * 
		 * { "providerAccount": { "id": 443434, "account": [{ "accountNumber":
		 * 232432, "accountType": "SAVINGS", "verification": { "type": "MATCHING" }
		 * }] } }
		 * 
		 * @param accountNumberKey
		 * @param accountNumberValue
		 * @param accountNameKey
		 * @param accountNameValue
		 * @param accountTypeKey
		 * @param accountTypeValue
		 * @param bankTransferIdKey
		 * @param bankTransferIdValue
		 * @param bankTransferTypeKey
		 * @param bankTransferTypeValue
		 * @param verificationTypeKey
		 * @param verificationTypeValue
		 * @return
		 * @throws JSONException
		 */
		public String createJsonForMatchingService(long providerAccountId,
				String accountNumber, String accountType, String verificationType)
				throws JSONException {

			JSONObject providerAccountsObject = new JSONObject();
			JSONObject providerAccounts = new JSONObject();

			JSONArray accountArray = new JSONArray();
			JSONObject account = new JSONObject();
			account.put("accountNumber", accountNumber);
			account.put("accountType", accountType);

			JSONObject verification = new JSONObject();
			verification.put("type", verificationType);

			account.put("verification", verification);
			accountArray.put(account);

			providerAccounts.put("account", accountArray);
			providerAccounts.put("id", providerAccountId);
			providerAccountsObject.put("providerAccount", providerAccounts);
			System.out.println("BODY PARAM FOR CDV INITIATION : \n\n\n"
					+ providerAccountsObject.toString(10));
			return providerAccountsObject.toString();
		}

	/**
	 * This method is used to create login form for MFA-Token challenge. It is used
	 * internally by API : POST /providerAccounts
	 * 
	 * { "loginForm": { "row": [{ "field": [{ "id": "token", "value": "123456" }]
	 * }]} }
	 * 
	 * @param idKey
	 *            is Constants.ID: It is key for token
	 * @param token
	 *            is Constants.TOKEN
	 * @param valueKey
	 *            Constants.VALUE: it is key for tokenValue
	 * @param tokenValue
	 *            is token value that is passed
	 * @return
	 * @throws JSONException
	 */
	public String createMFATokenObject(String idKey, String token, String valueKey, String tokenValue)
			throws JSONException {

		JSONObject loginForm = new JSONObject();
		JSONObject row = new JSONObject();
		JSONObject fieldData = new JSONObject();
		fieldData.put(idKey, token);
		fieldData.put(valueKey, tokenValue);

		JSONArray fieldArray = new JSONArray();
		fieldArray.put(fieldData);
		JSONObject field = new JSONObject();
		field.put("field", fieldArray);
		JSONArray rowArray = new JSONArray();
		rowArray.put(field);
		row.put("row", rowArray);
		loginForm.put("loginForm", row);
		System.out.println("loginform created in createMFATokenObject : " + loginForm.toString());

		return loginForm.toString();
	}

	/**
	 * This method is used to generate bodyParams for updating Manual Account
	 * i.e. @API: PUT /accounts/{accountId}
	 * 
	 * @param nickname
	 *            it is a nick name
	 * @param accountStatus
	 *            it is account status
	 * @param includeInNetWorth
	 *            it is includeInNetWorth
	 * @param memo
	 *            it is memo
	 * @param isEbillEnrolled
	 *            it is isEbillEnrolled
	 * @return
	 */
	public String createBodyParamsForUpdateAccount(String nickname, String accountStatus, String includeInNetWorth,
			String memo, String isEbillEnrolled) {

		JSONObject accountObj = new JSONObject();
		JSONObject accountUpdateParamObj = new JSONObject();

		if (nickname != null && !nickname.isEmpty()) {
			accountUpdateParamObj.put("nickname", nickname);
		}
		if (accountStatus != null && !accountStatus.isEmpty()) {
			accountUpdateParamObj.put("accountStatus", accountStatus);
		}
		if (includeInNetWorth != null && !includeInNetWorth.isEmpty()) {
			accountUpdateParamObj.put("includeInNetWorth", includeInNetWorth);
		}
		if (memo != null && !memo.isEmpty()) {
			accountUpdateParamObj.put("memo", memo);
		}
		if (isEbillEnrolled != null && !isEbillEnrolled.isEmpty()) {
			accountUpdateParamObj.put("isEbillEnrolled", isEbillEnrolled);
		}

		accountObj.put("account", accountUpdateParamObj);

		return accountObj.toString();
	}
	
	/**
	 * @author Soujanya Kokala
	 * @param providerId
	 * @param updatedLoginForm
	 * @return
	 */
	public HttpMethodParameters createInputParamsForAddProviderAccount(Long providerId, String updatedLoginForm) {
		Map<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put("providerId", String.valueOf(providerId));

		HttpMethodParameters httpParms = HttpMethodParameters.builder().build();
		httpParms.setQueryParams(queryParams);

		httpParms.setBodyParams(updatedLoginForm);
		return httpParms;
	}
	
	/**
	 * @author Soujanya Kokala
	 * @return
	 */
	public HttpMethodParameters createInputParamsForDeleteProviderAccount(String providerAccountId) {
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		HashMap<String, Object> pathParams = new HashMap<>();
		
		try {
			pathParams.put("providerAccountId", Long.valueOf(providerAccountId));
		}catch (Exception e) {
			pathParams.put("providerAccountId", providerAccountId);
		}
		
		httpMethodParameters.setPathParams(pathParams);
		return httpMethodParameters;
	}

	
	
	
	/**
	 * @author Soujanya Kokala
	 * @param providerId
	 * @param loginFormType
	 * @param dagUserName
	 * @param dagPassword
	 * @return
	 */
	public HttpMethodParameters createInputForAddProviderAcct(String providerId, String loginFormType,
			String dagUserName, String dagPassword,int rowLength) {
		
		Map<String, String> credentialMap = new HashMap<String, String>();
		credentialMap.put(Constants.DAG_USERNAME, dagUserName);
		credentialMap.put(Constants.DAG_PASSWORD, dagPassword);

	
		String updatedLoginFormWithCredentials= NonSdgLoginForm.getInstance().getUpdatedLoginFormWithCredentials(Long.valueOf(providerId), loginFormType, rowLength,
				credentialMap);
		
		System.out.println("updatedLoginFormWithCredentials:::"+updatedLoginFormWithCredentials);
		LinkedHashMap<String, Object> queryParams = new LinkedHashMap<>();
		
		try {
			queryParams.put(Constants.PROVIDER_ID_FILTER,Long.valueOf(providerId));
		}catch (Exception e) {
			queryParams.put(Constants.PROVIDER_ID_FILTER,providerId);
		}
		

		HttpMethodParameters httpParmsAddProviderAccount = HttpMethodParameters.builder().build();

		httpParmsAddProviderAccount.setBodyParams(updatedLoginFormWithCredentials);
		httpParmsAddProviderAccount.setQueryParams(queryParams);
	
		return httpParmsAddProviderAccount;
	}
	
	
	/**
	 * @author Soujanya kokala
	 * @param providerAccountId
	 * @param include
	 * @param requestId
	 * @return
	 */
	public HttpMethodParameters createInputForGetProviderAcctDetails(Long providerAccountId,String include,String requestId) {
		HttpMethodParameters httpParamsGetProviderAcctDetails=HttpMethodParameters.builder().build();
		
		Map<String, Object> pathParams=new HashMap<>();
		pathParams.put("providerAccountId", providerAccountId);
		httpParamsGetProviderAcctDetails.setPathParams(pathParams);
		
		if(include==null) {
			include="";
		}
		if(requestId==null) {
			requestId="";
		}
		Map<String, Object> queryParam=new HashMap<>();
		queryParam.put(Constants.INCLUDE_FILTER, include);
		queryParam.put("requestId", requestId);
		httpParamsGetProviderAcctDetails.setQueryParams(queryParam);
		
		return httpParamsGetProviderAcctDetails;
		
	}
	
	/**
	 * @author Soujanya Kokala
	 * @param providerAccountId
	 * @return
	 */
	public HttpMethodParameters createInputGetProviderAcctVerification(Long providerAccountId) {
		HttpMethodParameters httpParamsProviderAcctIdVerification=HttpMethodParameters.builder().build();
		Map<String, Object> pathParamsVerification=new HashMap<>();
		pathParamsVerification.put("providerAccountId", providerAccountId);
		httpParamsProviderAcctIdVerification.setPathParams(pathParamsVerification);
		return httpParamsProviderAcctIdVerification;
	}
	
	
	/**
	 * This method is used to create input json by creating update login form for
	 * adding account .
	 * 
	 * @param response
	 * @param dagUser
	 * @param dagPassword
	 * @param loginFormType
	 * @param isincludeDataSet
	 * @param includeDataSet
	 * @return
	 * @throws JSONException
	 */
	public String createInputForAccount(Long providerId, String dagUser, String dagPassword, String loginFormType,
			boolean isincludeDataSet, String includeDataSet) throws JSONException {

		String jsonString = null;
		String publicKey = null;
		String encryptedAccountName = null;
		String encryptedAccountPassword = null;
		CobrandUtils cobrandUtils = new CobrandUtils();

		if (Configuration.getInstance().getCobrandObj().isPKIEnabled() == true) {
			publicKey = cobrandUtils.getPublicKey(Configuration.getInstance().getCobrandSessionObj());
			System.out.println("Public key in createInputForAccount() is::" + publicKey);
			try {
				encryptedAccountName = EncryptionUtil.testEncrypt(dagUser, publicKey);
				encryptedAccountPassword = EncryptionUtil.testEncrypt(dagPassword, publicKey);
				dagUser = encryptedAccountName;
				dagPassword = encryptedAccountPassword;

			} catch (Exception e) {
				System.out.println("Exception thrown while encrptying dagUser/password");
				e.printStackTrace();
			}
		}

		NonSdgLoginForm nonSdgLoginForm = NonSdgLoginForm.getInstance();
		Map<String, String> credentialMap = new HashMap<String, String>();
		credentialMap.put("dagUsername", dagUser);
		credentialMap.put("dagPassword", dagPassword);

		if (isincludeDataSet) {
			jsonString = nonSdgLoginForm.getUpdatedLoginFormWithCredentials(providerId, loginFormType,
					Constants.TWO_COUNT, credentialMap);
		} else {
			jsonString = nonSdgLoginForm.getUpdatedLoginFormWithCredentials(providerId, loginFormType,
					Constants.TWO_COUNT, credentialMap);

			System.out.println("Json login form:" + jsonString);

		}

		return jsonString;
	}
	
	//Soujanya Kokala
	public HttpMethodParameters createInputParamforAcctAddition(Long providerId, String credentialsParam) {
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put("providerId", String.valueOf(providerId));

		HttpMethodParameters httpParms = HttpMethodParameters.builder().build();

		httpParms.setBodyParams(credentialsParam);
		httpParms.setQueryParams(queryParams);
		return httpParms;

	}
	
	/**
	 * This method is used create login form input for MFA security QA for both SDG
	 * and NON-SDG.
	 * 
	 * @param firstQues
	 *            it is first question asked
	 * @param firstAns
	 *            it is an answer for first question
	 * @param secondQues
	 *            it is second question asked
	 * @param secondAns
	 *            it is an answer for second question
	 * @return
	 * 
	 * 		{"loginForm":{"mfaTimeout": 90900,"formType":
	 *         "questionAndAnswer","row":[ {"id":
	 *         "SQandA--QUESTION_1--Row--1","fieldRowChoice": "0001","form":
	 *         "0001","label": + firstQues, "field": [{"id":
	 *         "SQandA--QUESTION_1--1","name": "QUESTION_1","isOptional":
	 *         false,"value": + firstAns,"valueEditable": "true","type": "text"}]},
	 *         {"id": "SQandA--QUESTION_2--Row--2","fieldRowChoice": "0002","form":
	 *         "0001","label": + secondQues, "field": [{"id":
	 *         "SQandA--QUESTION_2--2","name": "QUESTION_2","isOptional":
	 *         false,"value": + secondAns,"valueEditable": "true","type":
	 *         "text"}]}]}}
	 * 
	 */
	public String createInputForMFASecurityQANegative(String firstQues, String firstAns, String secondQues, String secondAns) {

		JSONObject loginFormObject = new JSONObject();
		JSONObject loginForm = new JSONObject();

		loginForm.put("mfaTimeout", "90900");
		loginForm.put("formType", "questionAndAnswer");

		JSONArray rowArray = new JSONArray();

		JSONObject row1 = new JSONObject();
		row1.put("id", "SQandA--QUESTION_1--Row--1");
		row1.put("fieldRowChoice", "0001");
		row1.put("form", "0001");

		JSONArray fieldArray1 = new JSONArray();
		JSONObject fieldObject1 = new JSONObject();
		fieldObject1.put("id", "SQandA--QUESTION_1--1");
		fieldObject1.put("name", "QUESTION_1");
		fieldObject1.put("isOptional", false);
		fieldObject1.put("value", firstAns);
		fieldObject1.put("valueEditable", "true");
		fieldObject1.put("type", "text");

		fieldArray1.put(fieldObject1);
		row1.put("field", fieldArray1);
		rowArray.put(row1);

		JSONObject row2 = new JSONObject();
		row2.put("id", "SQandA--QUESTION_2--Row--2");
		row2.put("fieldRowChoice", "0002");
		row2.put("form", "0001");

		JSONArray fieldArray2 = new JSONArray();
		JSONObject fieldObject2 = new JSONObject();
		fieldObject2.put("id", "SQandA--QUESTION_2--2");
		fieldObject2.put("name", "QUESTION_2");
		fieldObject2.put("isOptional", false);
		fieldObject2.put("value", secondAns);
		fieldObject2.put("valueEditable", "true");
		fieldObject2.put("type", "text");

		fieldArray2.put(fieldObject2);
		row2.put("field", fieldArray2);
		rowArray.put(row2);

		loginForm.put("row", rowArray);

		loginFormObject.put("loginForm", loginForm);
		System.out.println("The MFA SecurityQA Loginform is: \n" + loginFormObject.toString());

		return loginFormObject.toString();
	}
	
	//To GET UTC Time
	public Date getUTCtime() {
		Date utcTime = null;
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		// Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		// Time in GMT
		try {
			utcTime = dateFormatLocal.parse(dateFormatGmt.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return utcTime;
	}
}
