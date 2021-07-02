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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.TestTemplate;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;

import org.testng.Assert;



import io.restassured.response.Response;

// Author : Sankar Ganesh Viswanathan

//@Feature(featureName = {FeatureName.YSL FL Integration})
public class YSL_LightWeight_FL_Integration extends TestTemplate {
	
	String nodeUrl = "https://finapp.nextgenflpsd.corp.yodlee.com:3000/authenticate/psdnode/";
//	String nodeUrl = "https://mainpluspfmfinapp.corp.yodlee.com:4443/authenticate/mainpfm/";
	String finappId = "10003600";
	String siteId = "24016";    //24161, 19335, 24016;
	String token;
	String rSession;

	@BeforeClass(alwaysRun = true)
	public void setUpTest() {
		rSession = Configuration.getInstance().getCobrandSessionObj().getUserSession();
		UserUtils userUtils=new UserUtils();
		UserHelper userHelper=new UserHelper();
		String appIds="10003600";
		Map<String, Object> queryParam = userHelper.createQueryParamsGetAccessToken(appIds);
		HttpMethodParameters httpParams=HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		Response resp = userUtils.getAccessTokens(httpParams, Configuration.getInstance().getCobrandSessionObj());
		resp.then().log().all();
		JSONObject respAsJSONObj = new JSONObject(resp.asString());
		token = respAsJSONObj.getJSONObject("user").getJSONArray("accessTokens").getJSONObject(0).getString("value");
	}

	

	/**
	 * Test that validates LWFL with YSL.
	 */

	@Test()
	public void testYSL_LW_FL_Integration() {

		String extraParams = "callback=http://www.yodlee.com&flow=add&siteId=" + siteId;

		String url = createFLLauncher(getHTMLFormContent(rSession, token, extraParams));

		System.setProperty("webdriver.chrome.driver",

				System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
						+ "resources" + File.separator + "chromedriver.exe");
		
		
		
		ChromeOptions chromeOptions = new ChromeOptions();
		//added for handling -load unsafe scripts
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments("--disable-web-security");
		chromeOptions.addArguments("--allow-running-insecure-content");		

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		

		WebDriver driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
		
		driver.findElement(By.id("submit")).click();

		if (!driver.findElement(By.id("oauthContinueBtnId")).isDisplayed()) {
			Assert.fail("Failed to load End site as Light Weight FL for site id - " + siteId);
		}

		//driver.quit();

	}

	private String getHTMLFormContent(String rSession, String token, String extraParams) {

		String formHtmlContent = "<div class='center processText'>Processing...</div>" + "<div>"
				+ "<form action='${NODE_URL}' method='post' id='rsessionPost'>"
				+ "          RSession : <input type='text' name='rsession' placeholder='rsession' value='${RSESSION}' id='rsession'/><br/>"
				+ "          FinappId : <input type='text' name='app' placeholder='FinappId' value='${FINAPP_ID}' id='finappId'/><br/>"
				+ "          Redirect : <input type='text' name='redirectReq' placeholder='true/false' value='true'/><br/>"
				+ "          Token : <input type='text' name='token' placeholder='token' value='${TOKEN}' id='token'/><br/>"
				+ "          Extra Params : <input type='text' name='extraParams' placeholer='Extra Params' value='${EXTRA_PARAMS}' id='extraParams'/><br/>"
				+ "<input type='submit' value='Submit' id=submit>" + "</form></div>";

		return formHtmlContent.replace("${NODE_URL}", nodeUrl).replace("${FINAPP_ID}", finappId)
				.replace("${RSESSION}", rSession).replace("${TOKEN}", token).replace("${EXTRA_PARAMS}", extraParams);
	}

	private String createFLLauncher(String formHtmlContent) {
		String url = null;
		try {

			File file = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
					+ File.separator + "resources" + File.separator + "FastLinkLauncher.html");
			
			if (file.exists()) 	file.delete();
			
			file.createNewFile();

			System.out.println("File::" + formHtmlContent);

			FileWriter writer = new FileWriter(file);
			writer.write(formHtmlContent);
			writer.close();

			url = "file://" + file.getAbsolutePath().replace("\\", "/");

		} catch (Exception e) {

		}

		return url;

	}

}
