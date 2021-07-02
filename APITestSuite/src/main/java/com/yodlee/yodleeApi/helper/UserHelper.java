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
import java.util.Map;

import org.jose4j.lang.JoseException;
import org.json.JSONException;
import org.json.JSONObject;

import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JWTConstants;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;

public class UserHelper {

	/**
	 * 
	 * @param loginNameKey
	 * @param loginName
	 * @param passwordKey
	 * @param password
	 * @param localeKey
	 * @param locale
	 * @return
	 * @throws JSONException
	 */
	public String createJSONforUserLogin(String loginNameKey, String loginName, String passwordKey, String password,
			String localeKey, String locale) throws JSONException {
		User userInfo = User.builder().build();
		userInfo.setUsernameKey(loginNameKey);
		userInfo.setUsername(loginName);
		userInfo.setPasswordKey(passwordKey);
		userInfo.setPassword(password);
		userInfo.setPassword(localeKey);
		userInfo.setPassword(locale);
		return userRegisterJson(userInfo);

	}

	/**
	 * This method is used in both for creating input for update user details
	 * For reference in SDG: see UPDATE_USER_DETAILS.
	 * 
	 * API used: PUT /user
	 * 
	 * @param userInfo
	 *            - User Object which should have all the details set for
	 *            updating the user profile.
	 * @return
	 */
	public String createBodyParamsForUpdateUserProfile(User userInfo) {
		JSONObject updateUseJson = new JSONObject();

		JSONObject userObject = new JSONObject();

		JSONObject nameObj = new JSONObject();
		JSONObject addressObj = new JSONObject();
		JSONObject preferenceObj = new JSONObject();
		nameObj.put("first", userInfo.getFirst());
		nameObj.put("last", userInfo.getLast());
		addressObj.put("address1", userInfo.getAddress1());
		addressObj.put("address2", userInfo.getAddress2());
		addressObj.put("state", userInfo.getState());
		addressObj.put("city", userInfo.getCity());
		addressObj.put("zip", userInfo.getZip());
		addressObj.put("country", userInfo.getCountry());
		preferenceObj.put("currency", userInfo.getCurrency());
		preferenceObj.put("timeZone", userInfo.getTimeZone());
		preferenceObj.put("dateFormat", userInfo.getDateFormat());
		preferenceObj.put("locale", userInfo.getLocale());

		userObject.put("name", nameObj);
		userObject.put("address", addressObj);
		userObject.put("preferences", preferenceObj);
		userObject.put("email", userInfo.getEmailId());

		updateUseJson.put("user", userObject);

		String updateUserJSONString = updateUseJson.toString();

		return updateUserJSONString;

	}

	public String createUserRegistrationObject(String password, String email, String firstName, String lastName,
			String address1, String address2, String state, String city, String zip, String country, String currency,
			String timeZone, String dateFormat) {

		User userInfo = User.builder().build();
		userInfo.setUsername(Constants.YSL_USER + System.currentTimeMillis());
		userInfo.setPassword(password);
		userInfo.setEmailId(email);
		userInfo.setFirst(firstName);
		userInfo.setLast(lastName);
		userInfo.setAddress1(address1);
		userInfo.setAddress2(address2);
		userInfo.setState(state);
		userInfo.setCity(city);
		userInfo.setZip(zip);
		userInfo.setCountry(country);
		userInfo.setCurrency(currency);
		userInfo.setTimeZone(timeZone);
		userInfo.setDateFormat(dateFormat);

		return userRegisterJson(userInfo);

	}

	public String createUserRegistrationObject(User userInfo) {

		return userRegisterJson(userInfo);

	}

	public String userRegistrationObject(String userName, String password) throws JSONException {

		User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword(password);
		userInfo.setEmailId("yslRest19@yodlee.com");

		return userRegisterJson(userInfo);
	}

	/**
	 * @author Soujanya Kokala
	 * @param userInfo
	 * @return
	 */
	public String userRegisterJson(User userInfo) {
		String username = "";
		String password = "";

		Configuration configurationObj = Configuration.getInstance();

		JSONObject userObj = new JSONObject();
		JSONObject userRegisterObj = new JSONObject();
		if (userInfo.getUsername() == null || userInfo.getUsername().isEmpty()) {
			if (configurationObj.isUserStatic()) {
				username = configurationObj.getUserObj().getUsername();
				password = configurationObj.getUserObj().getPassword();
			} else {

				username = userInfo.getUsername();
				password = userInfo.getPassword();
				// throw new RuntimeException("Failed to register the user
				// because
				// Username/Password is empty");
			}
		} else {
			username = userInfo.getUsername();
			password = userInfo.getPassword();
		}
		userObj.put(userInfo.getUsernameKey(), username);

		userObj.put(userInfo.getPasswordKey(), password);

		if (userInfo.getEmailId() == null || userInfo.getEmailId().isEmpty()) {
			userObj.put(userInfo.getEmailIdKey(), "email@yodlee.com");
		} else {
			userObj.put(userInfo.getEmailIdKey(), userInfo.getEmailId());
		}
		JSONObject nameObj = new JSONObject();
		JSONObject addressObj = new JSONObject();
		JSONObject preferenceObj = new JSONObject();
		nameObj.put(userInfo.getFirstKey(), userInfo.getFirst());
		nameObj.put(userInfo.getLastKey(), userInfo.getLast());
		addressObj.put(userInfo.getAddress1Key(), userInfo.getAddress1());
		addressObj.put(userInfo.getAddress2Key(), userInfo.getAddress2());
		addressObj.put(userInfo.getStateKey(), userInfo.getState());
		addressObj.put(userInfo.getCityKey(), userInfo.getCity());
		addressObj.put(userInfo.getZipKey(), userInfo.getZip());
		addressObj.put(userInfo.getCountryKey(), userInfo.getCountry());
		preferenceObj.put(userInfo.getCurrencyKey(), userInfo.getCurrency());
		preferenceObj.put(userInfo.getTimeZoneKey(), userInfo.getTimeZone());
		preferenceObj.put(userInfo.getDateFormatKey(), userInfo.getDateFormat());
		preferenceObj.put(userInfo.getLocaleKey(), userInfo.getLocale());
		
		if(userInfo.getSegment() != null){
			
		userObj.put("segmentName",userInfo.getSegment());
		}

		userObj.put("name", nameObj);
		userObj.put("address", addressObj);
		userObj.put("preferences", preferenceObj);

		userRegisterObj.put("user", userObj);
		String userRegisterObjJSON = userRegisterObj.toString();
		System.out.println("userRegisterObjJSON is : " + userRegisterObjJSON);

		return userRegisterObjJSON;
	}

	public String createJSONforUserRegister(String loginNameKey, String loginName, String passwordKey, String password,
			String email) {

		User userInfo = User.builder().username(loginName).password(password).emailId(email).usernameKey(loginNameKey)
				.passwordKey(passwordKey).emailIdKey("email").build();
		// User userInfo = new User(loginName, password, email, loginNameKey,
		// passwordKey, "email");

		return userRegisterJson(userInfo);
	}

	public String createJSONforUserRegister(String loginNameKey, String loginName, String passwordKey, String password,
			String email, String localeKey, String locale) {

		// User userInfo = new User(loginName, password, email, locale,
		// loginNameKey,
		// passwordKey, "email", localeKey);

		User userInfo = User.builder().username(loginName).password(password).emailId(email).usernameKey(loginNameKey)
				.passwordKey(passwordKey).emailIdKey("email").locale(locale).localeKey(localeKey).build();

		return userRegisterJson(userInfo);
	}
	
	public String createJSONforUserRegisterObject(User userInfo) {

		// User userInfo = new User(loginName, password, email, locale,
		// loginNameKey,
		// passwordKey, "email", localeKey);

	

		return userRegisterJson(userInfo);
	}

	public String updatePasswordwithOldPasswordJSON(String loginName, String oldPassword, String newPassword)
			throws JSONException {
		JSONObject updateCredrntialsJSON = new JSONObject();
		JSONObject updateCredentialObject = new JSONObject();
		updateCredentialObject.put("loginName", loginName);
		updateCredentialObject.put("oldPassword", oldPassword);
		updateCredentialObject.put("newPassword", newPassword);
		updateCredrntialsJSON.put("user", updateCredentialObject);

		return updateCredrntialsJSON.toString();
	}

	public String getResetPassword(String loginNameKey, String loginNameValue, String tokenKey, String tokenValue,
			String newPasswordKey, String newPasswordValue) throws JSONException {
		JSONObject updateCredrntialsJSON = new JSONObject();
		JSONObject updateCredentialObject = new JSONObject();
		updateCredentialObject.put(loginNameKey, loginNameValue);
		if (tokenValue.trim().equalsIgnoreCase("")) {
			updateCredentialObject.put(tokenKey, tokenValue);
		} else if (tokenValue.trim().equalsIgnoreCase("Invalid")) {
			updateCredentialObject.put(tokenKey, "c719f8c@1223#$");
		} else if (tokenValue.trim().equalsIgnoreCase("Expired")) {
			updateCredentialObject.put(tokenKey, "c719f8c2ece4afe1c758e3d59b31925f");
		} else {
			updateCredentialObject.put(tokenKey, tokenValue);
		}
		updateCredentialObject.put(newPasswordKey, newPasswordValue);
		updateCredrntialsJSON.put("user", updateCredentialObject);
		return updateCredrntialsJSON.toString();
	}

	public String updatePasswordwithOldPassword(String loginNameKey, String loginNameValue, String oldPasswordKey,
			String oldPasswordValue, String newPasswordKey, String newPasswordValue) throws JSONException {
		JSONObject updateCredrntialsJSON = new JSONObject();
		JSONObject updateCredentialObject = new JSONObject();
		if (loginNameKey.trim().equalsIgnoreCase("VALID")) {
			updateCredentialObject.put("loginName", loginNameValue);
		} else {
			updateCredentialObject.put(loginNameKey, loginNameValue);
		}
		if (oldPasswordKey.trim().equalsIgnoreCase("VALID")) {
			updateCredentialObject.put("oldPassword", oldPasswordValue);
		} else {
			updateCredentialObject.put(oldPasswordKey, oldPasswordValue);
		}
		if (newPasswordKey.trim().equalsIgnoreCase("VALID")) {
			updateCredentialObject.put("newPassword", newPasswordValue);
		} else {
			updateCredentialObject.put(newPasswordKey, newPasswordValue);
		}
		updateCredrntialsJSON.put("user", updateCredentialObject);

		return updateCredrntialsJSON.toString();
	}

	public String userCredentialsTokenJSON(String loginName, String token, String newPassword) throws JSONException {
		JSONObject userCredentialsParam = new JSONObject();
		JSONObject userLoginObj = new JSONObject();
		userCredentialsParam.put("loginName", loginName);
		userCredentialsParam.put("token", token);

		userCredentialsParam.put("newPassword", newPassword);

		userLoginObj.put("user", userCredentialsParam);
		String userCredentialsObjJSON = userLoginObj.toString();
		System.out.println("userCredentialsObjJSON is : " + userCredentialsObjJSON);
		return userCredentialsObjJSON;
	}

	/**
	 * To create Json for UserRegistration
	 * 
	 * @param loginNameKey
	 * @param loginName
	 * @param passwordKey
	 * @param password
	 * @param emailKey
	 * @param email
	 * @param firstKey
	 * @param first
	 * @param lastKey
	 * @param last
	 * @param address1Key
	 * @param address1
	 * @param stateKey
	 * @param state
	 * @param cityKey
	 * @param city
	 * @param zipKey
	 * @param zip
	 * @param countryKey
	 * @param country
	 * @param currencyKey
	 * @param currency
	 * @param timeZoneKey
	 * @param timeZone
	 * @param dateFormatKey
	 * @param dateFormat
	 * @param localeKey
	 * @param locale
	 * @param nameKey
	 * @param addressKey
	 * @param preferencesKey
	 * @param userKey
	 * @return
	 * @throws JSONException
	 */
	public String userRegisterJson(String loginNameKey, String loginName, String passwordKey, String password,
			String emailKey, String email, String firstKey, String first, String lastKey, String last,
			String address1Key, String address1, String stateKey, String state, String cityKey, String city,
			String zipKey, String zip, String countryKey, String country, String currencyKey, String currency,
			String timeZoneKey, String timeZone, String dateFormatKey, String dateFormat, String localeKey,
			String locale, String nameKey, String addressKey, String preferencesKey, String userKey)
			throws JSONException {
		JSONObject userObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject addressObj = new JSONObject();
		JSONObject userRegisterObj = new JSONObject();
		JSONObject preferenceObj = new JSONObject();
		if (loginNameKey != null && !loginNameKey.equalsIgnoreCase(""))
			userObj.put(loginNameKey, loginName);
		Configuration configurationObj = Configuration.getInstance();

		if (configurationObj.getAuthenticationScheme().equalsIgnoreCase(JWTConstants.LEGACY)) {// Added
																								// to
																								// support
																								// for
																								// JWT
																								// Token.
			if (passwordKey != null && !passwordKey.equalsIgnoreCase(""))
				userObj.put(passwordKey, password);
		}
		if (emailKey != null && !emailKey.equalsIgnoreCase(""))
			userObj.put(emailKey, email);
		if (firstKey != null && !firstKey.equalsIgnoreCase(""))
			nameObj.put(firstKey, first);
		if (lastKey != null && !lastKey.equalsIgnoreCase(""))
			nameObj.put(lastKey, last);
		if (address1Key != null && !address1Key.equalsIgnoreCase(""))
			addressObj.put(address1Key, address1);
		if (stateKey != null && !stateKey.equalsIgnoreCase(""))
			addressObj.put(stateKey, state);
		if (cityKey != null && !cityKey.equalsIgnoreCase(""))
			addressObj.put(cityKey, city);
		if (zipKey != null && !zipKey.equalsIgnoreCase(""))
			addressObj.put(zipKey, zip);
		if (countryKey != null && !countryKey.equalsIgnoreCase(""))
			addressObj.put(countryKey, country);
		if (currencyKey != null && !currencyKey.equalsIgnoreCase(""))
			preferenceObj.put(currencyKey, currency);
		if (timeZoneKey != null && !timeZoneKey.equalsIgnoreCase(""))
			preferenceObj.put(timeZoneKey, timeZone);
		if (dateFormatKey != null && !dateFormatKey.equalsIgnoreCase(""))
			preferenceObj.put(dateFormatKey, dateFormat);
		if (localeKey != null && !localeKey.equalsIgnoreCase(""))
			preferenceObj.put(localeKey, locale);
		if (nameKey != null && !nameKey.equalsIgnoreCase(""))
			userObj.put(nameKey, nameObj);
		if (addressKey != null && !addressKey.equalsIgnoreCase(""))
			userObj.put(addressKey, addressObj);
		if (preferencesKey != null && !preferencesKey.equalsIgnoreCase(""))
			userObj.put(preferencesKey, preferenceObj);
		if (userKey != null && !userKey.equalsIgnoreCase(""))
			userRegisterObj.put(userKey, userObj);
		String userRegisterObjJSON = userRegisterObj.toString();
		System.out.println("userRegisterObjJSON is : " + userRegisterObjJSON);
		return userRegisterObjJSON;
	}

	public String userLoginJSON(String loginName, String password) throws JSONException {
		JSONObject userLoginInputParam = new JSONObject();
		JSONObject userLoginObj = new JSONObject();
		userLoginInputParam.put("loginName", loginName);

		userLoginInputParam.put("password", password);

		userLoginObj.put("user", userLoginInputParam);
		String userLoginObjJSON = userLoginObj.toString();
		System.out.println("User Login Param is : " + userLoginObjJSON);
		return userLoginObjJSON;
	}

	public String userCredentialsJSON(String loginName, String oldPassword, String newPassword) throws JSONException {
		JSONObject userCredentialsParam = new JSONObject();
		JSONObject userLoginObj = new JSONObject();
		userCredentialsParam.put("loginName", loginName);

		userCredentialsParam.put("oldPassword", oldPassword);

		userCredentialsParam.put("newPassword", newPassword);
		userLoginObj.put("user", userCredentialsParam);
		String userCredentialsObjJSON = userLoginObj.toString();
		System.out.println("manualTxnObjectJSON is : " + userCredentialsObjJSON);
		return userCredentialsObjJSON;
	}

	public Map<String, Object> createQueryParamsGetAccessToken(String appIds) {

		Map<String, Object> queryParam = new LinkedHashMap<>();

		if (appIds.equalsIgnoreCase("NOAPPID")) {
			queryParam.put("appIds", "");
		} else if (appIds.equalsIgnoreCase("NOAPPSTRING")) {
			queryParam.put("", "");
		} else if (appIds.equalsIgnoreCase("INVALIDAPPSTRING")) {
			queryParam.put("appi", "");
		} else {
			queryParam.put("appIds", appIds);
		}
		return queryParam;
	}

	public String createJSONforUserLogin(User user) throws JSONException {
		JSONObject userLoginInputParam = new JSONObject();
		JSONObject userLoginObj = new JSONObject();
		if (user.getUsername() != null && user.getUsername() != "")
			userLoginInputParam.put("loginName", user.getUsername());
		if (user.getPassword() != null && user.getPassword() != "")
			userLoginInputParam.put("password", user.getPassword());
		if (user.getLocale() != null && user.getLocale() != "")
			userLoginInputParam.put("locale", user.getLocale());
		userLoginObj.put("user", userLoginInputParam);
		String userLoginObjJSON = userLoginObj.toString();
		return userLoginObjJSON;
	}

	public void getUserSession(User user, EnvSession sessionObj)  {

		UserUtils userUtils = new UserUtils();
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
        httpParams.setBodyParams(userRegisterJson(user));
		String userSession = userUtils.userRegistration(sessionObj, httpParams);
		sessionObj.setUserSession(userSession);
		System.out.println("userSessionToken with new user:::" + userSession);
	}
	public void getUserSession(String userName,String passwd, EnvSession sessionObj) throws JoseException {

		UserUtils userUtils = new UserUtils();
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build(); 
	     httpParams.setBodyParams(userLoginJSON(userName, passwd));
		String userSession = userUtils.userLogin(httpParams, sessionObj);
		sessionObj.setUserSession(userSession);
		System.out.println("userSessionToken with new user:::" + userSession);
	}

	/**
	 * @author Soujanya Kokala
	 * @param userInfo
	 *            - User Object with all the values for User registration.
	 * @return
	 */
	public HttpMethodParameters createInputForUserReg(User userInfo) {
		String bodyParams = userRegisterJson(userInfo);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParams);
		return httpMethodParameters;
	}

	/**
	 * @param loginName
	 * @param password
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param address1
	 * @param address2
	 * @param state
	 * @param city
	 * @param zip
	 * @param country
	 * @param currency
	 * @param timeZone
	 * @param dateFormat
	 * @param locale
	 * @return
	 */
	public User createUserRegistration(String loginName, String password, String email, String firstName,
			String lastName, String address1, String address2, String state, String city, String zip, String country,
			String currency, String timeZone, String dateFormat, String locale) {

		User userInfo = User.builder().build();
		userInfo.setUsername(loginName);
		userInfo.setPassword(password);
		userInfo.setEmailId(email);
		userInfo.setFirst(firstName);
		userInfo.setLast(lastName);
		userInfo.setAddress1(address1);
		userInfo.setAddress2(address2);
		userInfo.setState(state);
		userInfo.setCity(city);
		userInfo.setZip(zip);
		userInfo.setCountry(country);
		userInfo.setCurrency(currency);
		userInfo.setTimeZone(timeZone);
		userInfo.setDateFormat(dateFormat);
		userInfo.setLocale(locale);

		return userInfo;

	}

	public String getUserSession(String usersSession, Session session) {
		String userSession = usersSession;
		if (userSession.equalsIgnoreCase("correct"))
			userSession = session.getUserSession();
		return userSession;
	}
}
