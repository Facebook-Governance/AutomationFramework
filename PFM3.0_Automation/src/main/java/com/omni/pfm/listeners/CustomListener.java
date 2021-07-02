/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.listeners;

import java.awt.image.BufferedImage;
/**
 * @author Suhaib
 *
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.constants.PageConstants;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;
import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class CustomListener extends TestListenerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CustomListener.class);
	static String envName;

	@Override
	public void onTestStart(ITestResult tr) {
		String methodName = tr.getMethod().getMethodName();
		String desc = "No Description Provided";
		if (tr.getMethod().getDescription() != null) {
			desc = tr.getMethod().getDescription();
		}
		TestBase.tc = TestBase.extent.startTest(methodName, desc);
		TestBase.test.appendChild(TestBase.tc);
		logger.info("==> Entering {} test method.", methodName);
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		String methodName = tr.getMethod().getMethodName();
		TestBase.tc = TestBase.extent.startTest(methodName);
		TestBase.tc.log(LogStatus.SKIP, "Skip");
		TestBase.test.appendChild(TestBase.tc);
		TestBase.extent.endTest(TestBase.tc);
		TestBase.extent.flush();

	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		TestBase.tc.log(LogStatus.PASS, "Test Passed...");
		String methodName = tr.getMethod().getMethodName();
		logger.info("<== Exiting {} test method.", methodName);
		SeleniumUtil.switchToDefaultContent(TestBase.getDriver());

	}

	@Override
	public void onTestFailure(ITestResult tr) {
		EReporter.log(LogStatus.INFO,
				"------------------------ Checking and Closing the Account Floater If it is opened After Test Failure ------------------------");
		if (PageParser.isElementPresent("AddAccountPopUpCloseLink", PageConstants.AccountsPage, null)) {
			logger.info("Verify that the Add account fl is not open");
			By popupClose = SeleniumUtil.getByObject(PageConstants.AccountsPage, null, "AddAccountPopUpCloseLink");
			if (SeleniumUtil.isDisplayed(popupClose, 4)) {
				SeleniumUtil.click(popupClose);
			}
		}
		EReporter.log(LogStatus.INFO,
				"------------------------ END Of Checking and Closing the Account Floater If it is opened After Test Failure ------------------------");

		logger.info("Reverting web driver to default handle because of test failure");
		TestBase.tc.log(LogStatus.FAIL, "Test Failed...");
		SeleniumUtil.switchToDefaultContent(TestBase.getDriver());
		// TestBase.getDriver().switchTo().defaultContent();
		String methodName = tr.getMethod().getMethodName();
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		WebDriver driver = TestBase.getDriver();
		// File scrFile = ((TakesScreenshot)
		// driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = "./screenshots";
		String destDir2 = "c:/ELPAM/screenshots";
		new File(destDir).mkdirs();
		String destFile = methodName + "_" + dateFormat.format(new Date()) + ".png";
		try {
//			captureScreenshot(driver, destDir + "/" + destFile, destDir2 + "/" + destFile);
		} catch (Exception e1) {
			logger.error("Error capturing the failure screenshot - {}", e1.getMessage());
			EReporter.log(LogStatus.INFO, "Error capturing the failure screenshot - " + e1.getMessage());
			e1.printStackTrace();
		}
		/*
		 * try { FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		 * FileUtils.copyFile(scrFile, new File(destDir2 + "/" + destFile)); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */
		Reporter.setEscapeHtml(false);
		Reporter.log("<a target='_blank' href=\"file://" + destDir2 + "/" + destFile + "\">Screenshot</a>");
		TestBase.tc.addScreenCapture(destDir + "/" + destFile);
		TestBase.tc.log(LogStatus.INFO,
				"<a target='_blank' href=\"../" + "screenshots" + "/" + destFile + "\">Screenshot</a>");
		try {
			if (SeleniumUtil.isDisplayed(By.linkText("Close"), 4)) {
				SeleniumUtil.click(By.linkText("Close"));
			}
		} catch (Exception ex) {
			// TestBase.getDriver().navigate().refresh();
		}
		if (tr.getThrowable() != null) {
			Writer result = new StringWriter();
			PrintWriter printWriter = new PrintWriter(result);
			tr.getThrowable().printStackTrace(printWriter);
			logger.error("Exception thrown during the Test : {}", tr.getName());
			logger.error(result.toString());
			TestBase.tc.log(LogStatus.INFO, result.toString());
		}
		// mk
		logger.info("<== Exiting {} test method.", methodName);
		EReporter.log(LogStatus.INFO,
				"########################## Checking that the site is not Logged- Out #########################################");

		/* s */
		EReporter.log(LogStatus.INFO,
				"########################## END OF Checking that the site is not Logged- Out #########################################");

		// TestBase.getDriver().navigate().refresh();

	}

	static String stackTraceToStirng(StackTraceElement[] stack) {
		String exception = "";
		for (StackTraceElement s : stack) {
			exception = exception + s.toString() + "\n\t\t";
		}
		return exception;
	}

	public static void captureScreenshot(WebDriver driver, String filename1, String fileName2) throws IOException {
		BufferedImage img = null;
		// String imageFilePath =
		// PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + filename
		// + ".png";
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName();
		String platformName = cap.getPlatform().toString();
		// Take screenshot and save to file
		if (browserName.equalsIgnoreCase("firefox")) {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			img = ImageIO.read(screenshot);
		} else if (platformName.equalsIgnoreCase("Mac")) {
			Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(600, 0, 0, 2))
					.takeScreenshot(driver);
			img = myScreenshot.getImage();
		} else {
			Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(600))
					.takeScreenshot(driver);
			img = myScreenshot.getImage();
		}
		File origscr1 = new File(filename1);
		File origsrc2 = new File(fileName2);
		ImageIO.write(img, "png", origscr1);
//		ImageIO.write(img, "png", origsrc2);
	}
}
