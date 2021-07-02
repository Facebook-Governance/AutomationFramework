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
package com.yodlee.app.yodleeApi.psd2.FLIntegration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Rest_LightWeight_FL_Integration {

	private String restUrl;
	private String finappId;

	private static String COB_LOGIN_URL = "authenticate/coblogin";
	private static String USER_LOGIN_URL = "authenticate/login";
	private static String GET_TOKEN = "authenticator/token";
	private static String AUTHENTICATE = "authenticator/authenticate";

	private String paramNameUserLogin = "login";
	private String paramNameUserPassword = "password";
	private String paramNameCobSessionToken = "cobSessionToken";
	private String paramNameUserSessionToken = "rsession";
	private String paramNameCobrandLogin = "cobrandLogin";
	private String paramNameCobrandPassword = "cobrandPassword";
	private String paramNameFinAppId = "finAppId";

	String formHtmlContent = "<div class='center processText'>Processing...</div>" + "<div>"
			+ "<form action='${NODE_URL}' method='post' id='rsessionPost'>"
			+ "          RSession : <input type='text' name='rsession' placeholder='rsession' value='${RSESSION}' id='rsession'/><br/>"
			+ "          FinappId : <input type='text' name='app' placeholder='FinappId' value='${FINAPP_ID}' id='finappId'/><br/>"
			+ "          Redirect : <input type='text' name='redirectReq' placeholder='true/false' value='true'/><br/>"
			+ "          Token : <input type='text' name='token' placeholder='token' value='${TOKEN}' id='token'/><br/>"
			+ "          Extra Params : <input type='text' name='extraParams' placeholer='Extra Params' value='${EXTRA_PARAMS}' id='extraParams'/><br/>"
			+ "<input type='submit' value='Submit' id=submit>" + "</form></div>";
	// + "<script>document.getElementById('rsessionPost').submit();</script>";

	public static void main(String[] args) {

		String restUrl =
				// "https://rest.nextgenflpsd.corp.yodlee.com:8843/services/srest/qapsd/v1.0/";
				"http://192.168.112.254:8480/services/srest/qapsd/v1.0/";

		String nodeUrl = "https://finapp.nextgenflpsd.corp.yodlee.com:3000/authenticate/psdnode/";

		String cobrandLoginValue = "cobrand_10000004";
		String cobrandPasswordValue = "Yodlee@123";
		String userName = "psdRest1521553630";
		String password = "Test@123";
		String finappId = "10003600";
		String extraParams = "callback=http://www.yodlee.com&flow=add&siteId=24161"; // SiteIds: NW OB - 24161, NW old
																						// 11969 , Model 24016

		Rest_LightWeight_FL_Integration rsh = new Rest_LightWeight_FL_Integration(restUrl, finappId);

		Token token = rsh.loginCobrand(cobrandLoginValue, cobrandPasswordValue);
		if (token.getCobrandSessionToken() != null) {
			token = rsh.loginUser(token.getCobrandSessionToken(), userName, password);
			if (token.getUserSessionToken() != null) {
				token = rsh.getToken(token.getCobrandSessionToken(), token.getUserSessionToken());
			}
		}
		if (token.getErrorInfo() != null) {
			System.err.println(token.getErrorInfo().getDescription());
			System.err.println(token.getErrorInfo().getMessage());
		} else if (token.getToken() != null) {
			try {

				System.out.println("NODE URL is :" + nodeUrl + "\n" + "r Session is :" + token.getUserSessionToken()
						+ "\n" + "token is :" + token.getToken() + "\n" + "Finapp Id is :" + finappId + "\n"
						+ "Extra Params :" + extraParams + "\n");

				String data = rsh.formHtmlContent.replace("${NODE_URL}", nodeUrl)
						.replace("${RSESSION}", token.getUserSessionToken()).replace("${TOKEN}", token.getToken())
						.replace("${EXTRA_PARAMS}", extraParams).replace("${FINAPP_ID}", finappId);
				File file = new File("post.html");
				if (!file.exists()) {
					file.createNewFile();
				}

				System.out.println("File::" + data);

				FileWriter writer = new FileWriter(file);
				writer.write(data);
				writer.close();

				String url = "file://" + file.getAbsolutePath().replace("\\", "/");

				System.setProperty("webdriver.chrome.driver",

						System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
								+ File.separator + "resources" + File.separator + "chromedriver.exe");

				WebDriver driver = new ChromeDriver();
				driver.get(url);
				driver.findElement(By.id("submit")).click();				
			

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Token loginCobrand(String cobrandLoginValue, String cobrandPasswordValue) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		String url = this.restUrl + COB_LOGIN_URL;
		// User & Cobrand login can be done via normal rest or SOAP.
		System.out.println("Validating Cobrand by Connecting to URL " + url);
		String cobrandSessionToken = null;
		Token token = new Token();
		try {
			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobrandLogin, cobrandLoginValue);
			pm.addParameter(paramNameCobrandPassword, cobrandPasswordValue);
			HttpClient hc = new HttpClient();
			// int RC = hc.executeMethod(pm);
			int rc = hc.executeMethod(pm);
			System.out.println("Response Status Code:" + rc);
			String source = pm.getResponseBodyAsString();
			System.out.println("Response : " + source);
			JSONObject jsonObject = new JSONObject(source);
			if (jsonObject.has("Error")) {
				ErrorInfo errorInfo = new ErrorInfo("Cobrand Login Failed",
						(String) jsonObject.getJSONObject("Error").get("errorDetail"));
				token.setErrorInfo(errorInfo);
			} else {
				JSONObject cobConvCreds = jsonObject.getJSONObject("cobrandConversationCredentials");
				cobrandSessionToken = (String) cobConvCreds.get("sessionToken");
				token.setCobrandSessionToken(cobrandSessionToken);
				System.out.println("Cobrand Session :" + cobrandSessionToken.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorInfo errorInfo = new ErrorInfo("Cobrand Login Failed", (String) e.getMessage());
			token.setErrorInfo(errorInfo);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return token;
	}

	public Token loginUser(String cobrandSessionToken, String usernameValue, String passwordValue) {
		String userSessionToken = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();

		String url = this.restUrl + USER_LOGIN_URL;
		System.out.println("Validating User session by Connecting to URL " + url);
		Token token = new Token();
		token.setCobrandSessionToken(cobrandSessionToken);
		try {
			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameUserLogin, usernameValue);
			pm.addParameter(paramNameUserPassword, passwordValue);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);

			HttpClient hc = new HttpClient();
			int rc = hc.executeMethod(pm);
			System.out.println("Response Status Code:" + rc);
			String source = pm.getResponseBodyAsString();
			System.out.println("Response : " + source);
			JSONObject jsonObject = new JSONObject(source);
			if (jsonObject.has("Error")) {
				ErrorInfo errorInfo = new ErrorInfo("User Login Failed",
						(String) jsonObject.getJSONObject("Error").get("errorDetail"));
				token.setErrorInfo(errorInfo);
			} else {
				JSONObject userContext = jsonObject.getJSONObject("userContext");
				JSONObject userConvCreds = userContext.getJSONObject("conversationCredentials");
				userSessionToken = (String) userConvCreds.get("sessionToken");
				token.setUserSessionToken(userSessionToken);
				System.out.println("User Session Token : " + userSessionToken.length());
			}

		} catch (Exception e) {
			ErrorInfo errorInfo = new ErrorInfo("User Login Failed", e.getMessage());
			token.setErrorInfo(errorInfo);
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return token;
	}

	public Token getToken(String cobrandSessionToken, String userSessionToken) {
		String response = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String url = this.restUrl + GET_TOKEN;
		System.out.println("Generating Token by Connecting to URL " + url);

		Token token = new Token();
		token.setCobrandSessionToken(cobrandSessionToken);
		token.setUserSessionToken(userSessionToken);
		try {
			PostMethod pm = new PostMethod(url);
			pm.addParameter(paramNameCobSessionToken, cobrandSessionToken);
			pm.addParameter(paramNameUserSessionToken, userSessionToken);
			pm.addParameter(paramNameFinAppId, this.finappId);

			System.out.println("praveen" + pm.getRequestHeaders().toString());
			pm.removeRequestHeader("Set-Cookie");
			HttpClient hc = new HttpClient();
			int RC = hc.executeMethod(pm);
			System.out.println("Response Status Code : " + RC);
			System.out.println("Response:" + pm.getResponseBodyAsString());
			response = pm.getResponseBodyAsString();
			System.out.println("Token Response:" + response);
			JSONObject jsonObject = new JSONObject(response);
			if (jsonObject.has("Error")) {
				ErrorInfo errorInfo = new ErrorInfo("Token Generation Failed",
						(String) jsonObject.getJSONObject("Error").get("errorDetail"));
				token.setErrorInfo(errorInfo);
			} else {
				try {
					jsonObject = jsonObject.getJSONObject("finappAuthenticationInfos");
				} catch (Exception e) {
					JSONArray infoArray = jsonObject.getJSONArray("finappAuthenticationInfos");
					jsonObject = (JSONObject) infoArray.get(0);
				}
				String tokenStr = (String) jsonObject.get("token");
				System.out.println(jsonObject.get("finappId") + " token is : " + tokenStr.length());
				token.setToken(tokenStr);
			}
		} catch (Exception e) {
			ErrorInfo errorInfo = new ErrorInfo("Token Generation Failed", e.getMessage());
			token.setErrorInfo(errorInfo);
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return token;
	}

	public Rest_LightWeight_FL_Integration(String restUrl, String finappId) {
		this.restUrl = restUrl;
		this.finappId = finappId;
	}
}

class Token {

	private String cobrandSessionToken;

	private String userSessionToken;

	private String token;

	private ErrorInfo errorInfo;

	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getCobrandSessionToken() {
		return cobrandSessionToken;
	}

	public void setCobrandSessionToken(String cobrandSessionToken) {
		this.cobrandSessionToken = cobrandSessionToken;
	}

	public String getUserSessionToken() {
		return userSessionToken;
	}

	public void setUserSessionToken(String userSessionToken) {
		this.userSessionToken = userSessionToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

class ErrorInfo {

	private String message;

	private String description;

	public ErrorInfo(String message, String description) {
		this.message = message;
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}