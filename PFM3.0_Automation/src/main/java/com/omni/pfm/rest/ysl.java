/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author
 ******************************************************************************/
package com.omni.pfm.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.XmlBeans.Element;
import com.omni.pfm.annotations.Find;
import com.omni.pfm.config.Config;
import com.omni.pfm.factory.PageFactory;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.utility.GenericUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ysl {
	private static final Logger logger = LoggerFactory.getLogger(LoginPage_SAML3.class);
	private static final String p = "SAML_LOGIN";
	@Find(label = "SAML_LOGIN.issuer", page = p)
	static WebElement issuer;
	@Find(label = "SAML_LOGIN.SubjectName", page = p)
	static WebElement subjectName;
	@Find(label = "SAML_LOGIN.ConsumerUrl", page = p)
	static WebElement consumerUrl;
	@Find(label = "SAML_LOGIN.TargetUrl", page = p)
	static WebElement targetUrl;
	@Find(label = "SAML_LOGIN.ProxyUrl", page = p)
	static WebElement proxyUrl;
	@Find(label = "SAML_LOGIN.AttributeString1", page = p)
	static WebElement attributeString;
	@Find(label = "SAML_LOGIN.AttributeString2", page = p)
	static WebElement attributeString2;
	@Find(label = "SAML_LOGIN.AttributeString3", page = p)
	static WebElement attributeString3;
	@Find(label = "Signing_Key", page = p)
	static WebElement signingKey;
	@Find(label = "SSO_KEY", page = p)
	static WebElement ssoKey;
	@Find(label = "SAML_LOGIN.Submit1", page = p)
	static WebElement submit;
	@Find(label = "SAML_LOGIN.FINAPPID", page = p)
	static WebElement finAppId;
	@Find(label = "SAML_LOGIN.EXTRParams", page = p)
	static WebElement extrParams;
	@Find(label = "SAML_LOGIN.EXTRParams2", page = p)
	static WebElement extrParams2;

	public static String bankItemAccountId;
	public static String loanItemAccountId;

	public ysl(boolean urlEncodingflag, String baseURI) {

		RestAssured.urlEncodingEnabled = urlEncodingflag;
		RestAssured.baseURI = baseURI;
	}

	public static void main(String[] args) throws Exception

	{/*
		 * //shareAdvisorAccount(null,null,null);
		 * 
		 * ysl ysl1 = new ysl(true, "http://192.168.113.182:9080/ysl/ymcsso/v1/");
		 * //ysl1.getCobrandSessionToken("yodlee_10000004", "Yodlee@123", "ymcsso",
		 * "v1"); System.out.println(ysl1.getCobrandSessionToken("yodlee_10000004",
		 * "Yodlee@123", "ymcsso", "v1"));
		 */

		shareAdvisorAccount(null, null, null);

	}

	public static void shareAdvisorAccount(String samlResponse, String advisorName, String investorName)
			throws JSONException {
		ysl ysl1 = new ysl(true, PropsUtil.getEnvPropertyValue("yslsUrl").trim());
		// ysl1.getCobrandSessionToken("yodlee_10000004", "Yodlee@123", "ymcsso", "v1");
		String cobSession = ysl1.getCobrandSessionToken(PropsUtil.getEnvPropertyValue("yslCobrandLogin").trim(),
				PropsUtil.getEnvPropertyValue("yslCobrandPassword").trim(),
				PropsUtil.getEnvPropertyValue("yslCobrandName").trim(),
				PropsUtil.getEnvPropertyValue("yslVerson").trim());
		// System.out.println(ysl1.getCobrandSessionToken("yodlee_10000004",
		// "Yodlee@123", "ymcsso", "v1"));
		String conTocken = "{cobSession=" + cobSession + "}";
		String useSession = doSamlLogin(conTocken, samlResponse);
		getItemAccountId(cobSession, useSession, investorName);
	}

	// Site based
	// Method to call 'CobrandLogin' api and generate 'CobLoginSession Token'
	public String getCobrandSessionToken(String cobrandLogin, String cobrandPassword, String cobrand, String apiVersion)
			throws JSONException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cobrandLogin", cobrandLogin);
		params.put("cobrandPassword", cobrandPassword);

		JSONObject cob = new JSONObject();
		cob.put("cobrand", params);

		Response response = YslUtil.postCobLogin(cob.toString(), "/cobrand/login", cobrand, apiVersion);
		response.then().log().all();
		return response.jsonPath().getString("session.cobSession");
	}

	public static String doSamlLogin(String header, String samlResponse) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("samlResponse", samlResponse);
		params.put("issuer", PropsUtil.getEnvPropertyValue("yslIssuer").trim());
		params.put("source", PropsUtil.getEnvPropertyValue("yslSource").trim());
		// http://192.168.113.182:9080/ysl/user/samlLogin
		Response response = YslUtil.postQuery(header.toString(), params, "/user/samlLogin");
		response.then().log().all();
		return response.jsonPath().getString("user.session.userSession");

	}

	public static void getItemAccountId(String cobsession, String userSesion, String investorName)
			throws JSONException {
		Response response = YslUtil.postQuery("{cobSession=" + cobsession + ",userSession=" + userSesion + "}",
				"/accounts");
		response.then().log().all();
		JSONObject bankObject = new JSONObject(response.asString());
		System.out.println("Bank Object : " + bankObject.toString());
		JSONArray accountArray = bankObject.getJSONArray("account");
		List bankAccountIdList = new ArrayList<>();
		for (int count = 0; count < accountArray.length(); count++) {
			// String accountType = accountArray.getJSONObject(count).getString("id");
			long accountId = accountArray.getJSONObject(count).getLong("id");
			System.out.println(accountId);
			bankAccountIdList.add(accountId);

		}

		for (int i = 0; i < bankAccountIdList.size(); i++) {
			// JSONObject accountresponsebody =new JSONObject("{\"account\": {\"sharedTo\":
			// [{\"user\": {\"access\":
			// \"{\\\"access\\\":{\\\"accounts\\\":{\\\"version\\\":[\\\"v1\\\",\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\",\\\"PUT\\\"]},\\\"statements\\\":{\\\"version\\\":[\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\"]},\\\"transactions\\\":{\\\"version\\\":[\\\"v1\\\",\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\",\\\"PUT\\\"]},\\\"holdings\\\":{\\\"version\\\":[\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\"]}}}\",\"loginName\":
			// \"PFMINV1524574881693\"}}],\"nickname\": \"TEST\",\"isShared\": \"true\"}}");
			JSONObject accountresponsebody = new JSONObject(
					"{\"account\": {\"sharedTo\": [{\"user\": {\"access\": \"{\\\"access\\\":{\\\"accounts\\\":{\\\"version\\\":[\\\"v1\\\",\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\",\\\"PUT\\\"]},\\\"statements\\\":{\\\"version\\\":[\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\"]},\\\"transactions\\\":{\\\"version\\\":[\\\"v1\\\",\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\",\\\"PUT\\\"]},\\\"holdings\\\":{\\\"version\\\":[\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\"]}}}\",\"loginName\": \""
							+ investorName + "\"}}],\"nickname\": \"TEST\",\"isShared\": \"true\"}}");
			System.out.println(bankAccountIdList.get(i));
			System.out.println("/accounts/" + bankAccountIdList.get(i));
			YslUtil.putBodyQuery("{cobSession=" + cobsession + ",userSession=" + userSesion + "}",
					accountresponsebody.toString(), "/accounts/" + bankAccountIdList.get(i));
			response.then().log().all();

		}

	}

	public static String login(WebDriver d, String loginusername, String loginAsInvester) {
		String smalResponseForInvestorRegistration = null;

		logger.info("==> Entering SAML login");
		Element e;
		boolean result = true;
		if (d == null) {
			logger.error("WebDriver instance in null - {}", d);
		} else {
			d.get(PropsUtil.getEnvPropertyValue("SAML_BASE_URL"));
			d.manage().deleteAllCookies();
			d.navigate().refresh();
			SeleniumUtil.waitForPageToLoad(10000);
			try {
				if ("yes".equalsIgnoreCase(PropsUtil.getEnvPropertyValue("cnf_IFrameEnabled"))) {
					d.findElement(By.linkText("show LAW")).click();
					PageParser.switchToFrame(d, "SAML_LOGIN", "MAIN_FRAME");
				}
			} catch (Exception ex) {
			}
			WebElement changeConfig = SeleniumUtil.getWebElement(d, "ChangeConfiguration", "SAML_LOGIN", null);
			if (changeConfig != null) {
				changeConfig.click();
			}
			PageFactory.initalize(d, LoginPage_SAML3.class, "SAML_LOGIN");
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.issuer");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				issuer.clear();
				issuer.sendKeys(e.getElementValue().trim());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SubjectName");
			subjectName.clear();

			logger.info("User Name is : {}", loginusername);
			subjectName.sendKeys(loginusername);
			Reporter.log("User Name : " + loginusername);
			// Config.setUserName(userName);
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ConsumerUrl");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				consumerUrl.clear();
				consumerUrl.sendKeys(e.getElementValue());
			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.finAppId");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				finAppId.clear();
				finAppId.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.extrParams");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("CONSUMER URL IS : {}", e.getElementValue());
				if (loginAsInvester != null) {
					extrParams.clear();
					String extparamvalue = e.getElementValue() + "&IID=" + loginAsInvester;
					extrParams.sendKeys(extparamvalue);
				}

			}

			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AttributeString1");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				attributeString.clear();
				logger.info(e.getElementValue());

				attributeString.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SamlVersion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				d.findElement(By.xpath("//input[@id='SAML_VERSION' and @value='" + e.getElementValue().trim() + "']"))
						.click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.AssertionEncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())
					&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")) != null
					&& d.findElements(By.xpath("//input[@id='ASS_ENCRYPT_FLAG']")).get(0).isDisplayed()) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='true']")).click();
				} else
					d.findElement(By.xpath("//input[@id='ASS_ENCRYPT_FLAG' and @value='None']")).click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.MultipleKey");
			if (e != null && !GenericUtil.isNull(e.getElementValue())
					&& d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")) != null
					&& d.findElements(By.xpath("//input[@id='MULTI_KEY_FLAG']")).get(0).isDisplayed()) {
				if ("on".equalsIgnoreCase(e.getElementValue().trim())) {
					d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='true']")).click();
				} else
					d.findElement(By.xpath("//input[@id='MULTI_KEY_FLAG' and @value='None']")).click();
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncryptFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("OFF")) {
					WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']"));
					if (encryptFlag != null && encryptFlag.isDisplayed()) {
						d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='None']")).click();
					}
				} else if (e.getElementValue().trim().equalsIgnoreCase("ON")) {
					WebElement encryptFlag = d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']"));
					if (encryptFlag != null && encryptFlag.isDisplayed()) {
						d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='true']")).click();
					} else if (e.getElementValue().trim().equalsIgnoreCase("EBC")) {
						d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='EBC']")).click();
					} else if (e.getElementValue().trim().equalsIgnoreCase("CBC")) {
						d.findElement(By.xpath("//input[@id='ENCRYPT_FLAG' and @value='CBC']")).click();
					}
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.ATTRIB_ENCRYPTION_MECHANISM");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				List<WebElement> list = d.findElements(By.id(e.getValue()));
				for (WebElement we : list) {
					if (we.getAttribute("value").contains(e.getElementValue().trim())) {
						we.click();
						break;
					}
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.EncodeFlag");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("on")
						|| e.getElementValue().trim().equalsIgnoreCase("true")) {
					d.findElement(By.xpath("//input[@id='ENCODE_ATTR_FLAG' and @value='true']")).click();
				} else {
					d.findElement(By.xpath("//input[@id='ENCODE_ATTR_FLAG' and @value='false']")).click();
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SignResponse");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("true")) {
					d.findElement(By.xpath("//input[@id='SIGN_RES_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("false")) {
					d.findElement(By.xpath("//input[@id='SIGN_RES_FLAG' and @value='false']")).click();
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.SignAssertion");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("true")) {
					d.findElement(By.xpath("//input[@id='SIGN_ASSER_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("false")) {
					d.findElement(By.xpath("//input[@id='SIGN_ASSER_FLAG' and @value='false']")).click();
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "Signing_Key");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("Signing Key Value is : {}", e.getElementValue());
				if (signingKey != null && signingKey.isDisplayed()) {
					signingKey.clear();
					signingKey.sendKeys(e.getElementValue());
				}
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SSO_KEY");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				logger.info("SSO ATTRIB KEY Value is : {}", e.getElementValue());
				ssoKey.clear();
				ssoKey.sendKeys(e.getElementValue());
			}
			e = PageParser.getPageElement("SAML_LOGIN", "SAML_LOGIN.LinkIntegrityToken");
			if (e != null && !GenericUtil.isNull(e.getElementValue())) {
				if (e.getElementValue().trim().equalsIgnoreCase("true")) {
					d.findElement(By.xpath("//input[@id='LIT_FLAG' and @value='true']")).click();
				} else if (e.getElementValue().trim().equalsIgnoreCase("false")) {
					try {
						Thread.sleep(4000);
						// d.findElement(By.xpath("//input[@id='LIT_FLAG' and
						// @value='false']")).click();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
			SeleniumUtil.scrollToWebElementMiddle(d, submit);
			System.out.println("CLICKING SUBMIT");
			SeleniumUtil.click(submit);
			smalResponseForInvestorRegistration = d.findElement(By.id("cleanResponse")).getText();
			System.out.println("=================the saml response for resgsitration of a user===================\n\n");
			System.out.println(smalResponseForInvestorRegistration + "\n");
			d.quit();
		}
		return smalResponseForInvestorRegistration;
	}

	public static void shareAdvAcctWithOutNickName(String samlResponse, String advisorName, String investorName)
			throws JSONException {
		ysl ysl1 = new ysl(true, "http://192.168.113.182:9080/ysl/ymcsso/v1/");
		// ysl1.getCobrandSessionToken("yodlee_10000004", "Yodlee@123", "ymcsso", "v1");
		String cobSession = ysl1.getCobrandSessionToken("yodlee_10000004", "Yodlee@123", "ymcsso", "v1");
		// System.out.println(ysl1.getCobrandSessionToken("yodlee_10000004",
		// "Yodlee@123", "ymcsso", "v1"));
		String conTocken = "{cobSession=" + cobSession + "}";
		String useSession = doSamlLogin(conTocken, samlResponse);
		getItemAccountIdWithoutNickName(cobSession, useSession, investorName);
	}

	public static void getItemAccountIdWithoutNickName(String cobsession, String userSesion, String investorName)
			throws JSONException {
		Response response = YslUtil.postQuery("{cobSession=" + cobsession + ",userSession=" + userSesion + "}",
				"/accounts");
		response.then().log().all();
		JSONObject bankObject = new JSONObject(response.asString());
		System.out.println("Bank Object : " + bankObject.toString());
		JSONArray accountArray = bankObject.getJSONArray("account");
		List bankAccountIdList = new ArrayList<>();
		for (int count = 0; count < accountArray.length(); count++) {
			// String accountType = accountArray.getJSONObject(count).getString("id");
			long accountId = accountArray.getJSONObject(count).getLong("id");
			System.out.println(accountId);
			bankAccountIdList.add(accountId);

		}

		for (int i = 0; i < bankAccountIdList.size(); i++) {
			// JSONObject accountresponsebody =new JSONObject("{\"account\": {\"sharedTo\":
			// [{\"user\": {\"access\":
			// \"{\\\"access\\\":{\\\"accounts\\\":{\\\"version\\\":[\\\"v1\\\",\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\",\\\"PUT\\\"]},\\\"statements\\\":{\\\"version\\\":[\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\"]},\\\"transactions\\\":{\\\"version\\\":[\\\"v1\\\",\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\",\\\"PUT\\\"]},\\\"holdings\\\":{\\\"version\\\":[\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\"]}}}\",\"loginName\":
			// \"PFMINV1524574881693\"}}],\"nickname\": \"TEST\",\"isShared\": \"true\"}}");
			JSONObject accountresponsebody = new JSONObject(
					"{\"account\": {\"sharedTo\": [{\"user\": {\"access\": \"{\\\"access\\\":{\\\"accounts\\\":{\\\"version\\\":[\\\"v1\\\",\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\",\\\"PUT\\\"]},\\\"statements\\\":{\\\"version\\\":[\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\"]},\\\"transactions\\\":{\\\"version\\\":[\\\"v1\\\",\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\",\\\"PUT\\\"]},\\\"holdings\\\":{\\\"version\\\":[\\\"v1\\\"],\\\"method\\\":[\\\"GET\\\"]}}}\",\"loginName\": \""
							+ investorName + "\"}}],\"isShared\": \"true\"}}");
			System.out.println(bankAccountIdList.get(i));
			System.out.println("/accounts/" + bankAccountIdList.get(i));
			YslUtil.putBodyQuery("{cobSession=" + cobsession + ",userSession=" + userSesion + "}",
					accountresponsebody.toString(), "/accounts/" + bankAccountIdList.get(i));
			response.then().log().all();

		}

	}
}
