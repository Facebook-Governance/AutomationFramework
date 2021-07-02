package com.org.listeners;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.utils.ExceptionUtil;
import com.org.Util.BaseClass;

public class ReportGenerator implements ITestListener {
	public final static String PROJECT_HOME = System.getProperty("user.dir");
	private static String EXTENT_REPORT_FILE = PROJECT_HOME + File.separator + "NewExtentReports" + File.separator
			+ "CompleteReport" + new SimpleDateFormat("dd-MM-yyyy HH-mm").format(new Date()) + ".html";
	private String EXTENT_REPORT_PATH = PROJECT_HOME + File.separator + "NewExtentReports" + File.separator
			+ "SuiteWiseReports" + File.separator;
	private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	public String filename;
	private static ExtentHtmlReporter htmlreporter = new ExtentHtmlReporter(EXTENT_REPORT_FILE);
	private static ExtentHtmlReporter moduleWiseHTMLReporter;
	private ExtentReports extent = new ExtentReports();
	private ExtentReports moduleWiseExtent;
	public static ExtentTest logger;
	public static ExtentTest moduleWiseLogger;
	private String suiteName;
	private String methodParameters = "";
	public static int passed, failed, skipped;
	Date startTime, endTime;
	private static boolean didOnTestStartGotExecuted = false;

	public void onTestFailure(ITestResult result) {
		try {
			endTime = new Date();
			String testName = result.getName();
			String base64OfScreenShot = "";
			/*
			 * try { Screenshot screenshot = new
			 * AShot().shootingStrategy(ShootingStrategies.viewportPasting(600))
			 * .takeScreenshot(TestBase.getDriver()); base64OfScreenShot =
			 * imgToBase64String(screenshot.getImage(), "png"); } catch (Exception e) { }
			 */
			base64OfScreenShot = ((TakesScreenshot) BaseClass.driver).getScreenshotAs(OutputType.BASE64);
			logger.log(Status.INFO, "<img height=\"195\" width=\"195\" src='data:image/png;charset=utf-8;base64,"
					+ base64OfScreenShot
					+ "'  onmouseover=\"bigImg(this)\" onmouseout=\"normalImg(this)\"> Mouse Hover Here For Screenshot </img>"
					+ "<script> function bigImg(x) { x.style.height = \"500px\"; x.style.width = \"750px\";}  function normalImg(x)"
					+ " { x.style.height = \"195px\";  x.style.width = \"195px\";}</script>");
			moduleWiseLogger.log(Status.INFO,
					"<img height=\"195\" width=\"195\" src='data:image/png;charset=utf-8;base64," + base64OfScreenShot
							+ "'  onmouseover=\"bigImg(this)\" onmouseout=\"normalImg(this)\"> Mouse Hover Here For Screenshot </img>"
							+ "<script> function bigImg(x) { x.style.height = \"500px\"; x.style.width = \"750px\";}  function normalImg(x)"
							+ " { x.style.height = \"195px\";  x.style.width = \"195px\";}</script>");
			failed++;
			log.error("\n\n " + testName + methodParameters + " test has failed \n\n");
			logger.log(Status.FAIL, "<b><i> Failure Message :: </b></i>" + result.getThrowable().getMessage());
			moduleWiseLogger.log(Status.FAIL,
					"<b><i> Failure Message :: </b></i>" + result.getThrowable().getMessage());
			logger.log(Status.FAIL, "<div><b><i> Stacktrace :: </b></i></div><div><textarea>"
					+ ExceptionUtil.getStackTrace(result.getThrowable()) + "</textarea></div>");
			moduleWiseLogger.log(Status.FAIL, "<div><b><i> Stacktrace :: </b></i></div><div><textarea>"
					+ ExceptionUtil.getStackTrace(result.getThrowable()) + "</textarea></div>");
			logger.log(Status.INFO, "Total time taken for test case to execute :: "
					+ ((endTime.getTime() - startTime.getTime()) / 1000) + " Seconds");
			moduleWiseLogger.log(Status.INFO, "Total time taken for test case to execute :: "
					+ ((endTime.getTime() - startTime.getTime()) / 1000) + " Seconds");

			extent.flush();
			moduleWiseExtent.flush();
		} catch (Exception e) {
			log.error("After test failure things are getting failed due to  :: " + e.getMessage());
		} finally {
			didOnTestStartGotExecuted = false;
		}
	}

	/*
	 * private String takeScreenShot(String className) { String folderPath =
	 * System.getProperty("user.dir")+"/target/FailedScreenShots"; String filePath;
	 * String timeStamp = new
	 * SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime
	 * ()); filePath = folderPath + className + " " + timeStamp + ".png"; new
	 * File(folderPath).mkdirs(); // Insure directory is there File scrFile =
	 * ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); try {
	 * FileUtils.copyFile(scrFile, new File(filePath)); } catch (IOException e) {
	 * e.printStackTrace(); } log.info(" Placed screen shot in " + filePath); return
	 * filePath; }
	 */

	public void onFinish(ITestContext context) {
		log.info("----------------------------------------------------------------------------------------\n");
		log.info("****************************************************************************************\n");
	}

	public void onTestStart(ITestResult result) {
		didOnTestStartGotExecuted = true;
		String testName = result.getName();
		Object[] params = result.getParameters();
		methodParameters = "";
		try {
			if (params.length > 0) {
				methodParameters += " ( ";
				for (int i = 0; i < params.length; i++) {
					if (i == 0) {
						methodParameters += params[i].toString();
					} else {
						methodParameters += " , " + params[i].toString();
					}
				}
				methodParameters += " ) ";
				methodParameters = methodParameters.replace("*", "");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		log.info("\n\n" + "<< --- TestCase START --->> " + testName + methodParameters + "\n");
		logger = extent.createTest(testName + methodParameters);
		logger.assignCategory(suiteName);
		logger.log(Status.INFO, "<b><i>Test Case Name :: </b></i>\"" + testName + "\"");
		logger.log(Status.INFO,
				"<b><i>Description of the test :: </b></i> \"" + result.getMethod().getDescription() + "\"");
		moduleWiseLogger = moduleWiseExtent.createTest(testName + methodParameters);
		moduleWiseLogger.assignCategory(suiteName);
		moduleWiseLogger.log(Status.INFO, "<b><i>Test Case Name :: </b></i>\"" + testName + "\"");
		moduleWiseLogger.log(Status.INFO,
				"<b><i>Description of the test :: </b></i> \"" + result.getMethod().getDescription() + "\"");
		startTime = new Date();
		filename = testName;
	}

	public void onTestSuccess(ITestResult result) {
		passed++;
		endTime = new Date();
		String testName = result.getName();
		log.info("\n\n TestCase: " + testName + methodParameters + ": --->>> PASS \n");
		logger.log(Status.INFO, "Total time taken for test case to execute :: "
				+ ((endTime.getTime() - startTime.getTime()) / 1000) + " Seconds");
		logger.log(Status.PASS, testName + methodParameters + " test has passed");
		moduleWiseLogger.log(Status.INFO, "Total time taken for test case to execute :: "
				+ ((endTime.getTime() - startTime.getTime()) / 1000) + " Seconds");
		moduleWiseLogger.log(Status.PASS, testName + methodParameters + " test has passed");
		extent.flush();
		moduleWiseExtent.flush();
		didOnTestStartGotExecuted = false;
	}

	public void onTestSkipped(ITestResult result) {
		skipped++;
		String testName = result.getName();
		if (!didOnTestStartGotExecuted) {
			logger = extent.createTest(testName + methodParameters);
			logger.assignCategory(suiteName);
			moduleWiseLogger = moduleWiseExtent.createTest(testName + methodParameters);
			moduleWiseLogger.assignCategory(suiteName);
		}
		logger.log(Status.INFO,
				"<b><i>Description of the test :: </b></i>\"" + result.getMethod().getDescription() + "\"");
		logger.log(Status.SKIP, testName + methodParameters + " test skipped due to :: "
				+ ExceptionUtil.getStackTrace(result.getThrowable()));
		moduleWiseLogger.log(Status.INFO,
				"<b><i>Description of the test :: </b></i>\"" + result.getMethod().getDescription() + "\"");
		moduleWiseLogger.log(Status.SKIP, testName + methodParameters + " test skipped due to :: "
				+ ExceptionUtil.getStackTrace(result.getThrowable()));
		log.info("\n\n TestCase: " + testName + methodParameters + ": --->>> SKIPPED");
		extent.flush();
		moduleWiseExtent.flush();
		didOnTestStartGotExecuted = false;
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.info("\n\n TestCase: " + result.getName() + ": --->>> FAILED With percentage");
	}

	public void onStart(ITestContext context) {
		log.info("****************************************************************************************");
		log.info("                                " + context.getName() + "       ");
		log.info("----------------------------------------------------------------------------------------");
		File extentReports = new File(EXTENT_REPORT_PATH);
		if (!extentReports.exists()) {
			extentReports.mkdirs();
		}
		extent.attachReporter(htmlreporter);
		htmlreporter.config().setDocumentTitle("Sub-brand Tool Automation Report");
		htmlreporter.config().setReportName("Sub-brand Tool Automation Report");
		htmlreporter.config().setTheme(Theme.DARK);
		suiteName = seperateStringInCamelCase(context.getName());
		moduleWiseHTMLReporter = null;
		moduleWiseExtent = null;
		moduleWiseExtent = new ExtentReports();
		moduleWiseHTMLReporter = new ExtentHtmlReporter(
				EXTENT_REPORT_PATH + File.separator + suiteName.replace(":", "") + " Report.html");
		moduleWiseExtent.attachReporter(moduleWiseHTMLReporter);
		moduleWiseHTMLReporter.config().setDocumentTitle(suiteName + " Sub-brand Tool Automation Report");
		moduleWiseHTMLReporter.config().setReportName(suiteName + " Sub-brand Tool Automation Report");
		moduleWiseHTMLReporter.config().setTheme(Theme.STANDARD);

		passed = 0;
		failed = 0;
		skipped = 0;
	}

	public String seperateStringInCamelCase(String string) {
		try {
			return capitalizeTheFirstWord(
					StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(string), " ").trim());
		} catch (Exception e) {
			return string;
		}
	}

	public String capitalizeTheFirstWord(String name) {
		String s1 = name.substring(0, 1).toUpperCase();
		String nameCapitalized = s1 + name.substring(1);
		return nameCapitalized;
	}

	public static String imgToBase64String(final RenderedImage img, final String formatName) {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, formatName, Base64.getEncoder().wrap(os));
			return os.toString(StandardCharsets.ISO_8859_1.name());
		} catch (final IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	public static BufferedImage base64StringToImg(final String base64String) {
		try {
			return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(base64String)));
		} catch (final IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

}