/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.im4java.core.CompareCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.listeners.EReporter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/* 	Author: Ramesh Kumar N
 *  
 * 
 * 
 * 
 */
public class ImageCompareUtil {
    public static Properties properties = new Properties();
    private String browserName;
    private String platformName;

    public ImageCompareUtil() {
    }

    // Constructor to make available the browser and platform in which the suite is running
    public ImageCompareUtil(String browserName, String platformName) {
	this.browserName = browserName;
	this.platformName = platformName;
    }

    /*  This method takes driver object and name of the screenshot to be saved
     *  Saves the file in actualScreenshotFilePath specified in the properties file
     */
    public void captureScreenshot(WebDriver Driver, String filename) throws IOException {
	BufferedImage img = null;
	String imageFilePath = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + filename + ".png";
	// Take screenshot and save to file
	if (this.browserName.equalsIgnoreCase("firefox")) {
	    File screenshot = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
	    img = ImageIO.read(screenshot);
	} else if (this.platformName.equalsIgnoreCase("Mac")) {
	    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(600, 0, 0, 2)).takeScreenshot(Driver);
	    img = myScreenshot.getImage();
	} else {
	    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(600)).takeScreenshot(Driver);
	    img = myScreenshot.getImage();
	}
	File origscr = new File(imageFilePath);
	ImageIO.write(img, "png", origscr);
    }

    /* Overloaded method here we accept flag as true or false to say whether screenshot is enabled or disabled
     * Screenshot is captured only if screenShotCompareFlag is passed as true
     * 
     */
    public void captureScreenshot(WebDriver Driver, String filename, String screenShotCompareFlag) {
	if (screenShotCompareFlag.equalsIgnoreCase("true")) {
	    try {
		Thread.sleep(20000);
		BufferedImage img = null;
		String imageFilePath = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + filename + ".png";
		System.out.println(this.platformName);
		// Take screenshot and save to file
		if (this.browserName.equalsIgnoreCase("firefox")) {
		    File screenshot = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
		    img = ImageIO.read(screenshot);
		} else if (this.platformName.equalsIgnoreCase("Mac")) {
		    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(600, 0, 0, 2))
			    .takeScreenshot(Driver);
		    img = myScreenshot.getImage();
		} else {
		    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(600)).takeScreenshot(Driver);
		    img = myScreenshot.getImage();
		}
		File origscr = new File(imageFilePath);
		ImageIO.write(img, "png", origscr);
	    } catch (Exception ex) {
		Reporter.log("Could not able to take screenshot");
	    }
	} else {
	    return;
	}
    }

    /* Overloaded method here we accept flag as true or false to say whether screenshot is enabled or disabled
     * and boolean to say whether full page screenshot is needed or only visible portion in the browser
     * 
     */
    public void captureScreenshot(WebDriver Driver, String filename, String screenShotCompareFlag, Boolean onlyVisiblePart) {
	if (screenShotCompareFlag.equalsIgnoreCase("true")) {
	    try {
		Thread.sleep(20000);
		BufferedImage img = null;
		String imageFilePath = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + filename + ".png";
		System.out.println(this.platformName);
		// Take screenshot and save to file
		if (onlyVisiblePart) {
		    File screenshot = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
		    img = ImageIO.read(screenshot);
		} else if (this.platformName.equalsIgnoreCase("Mac") && !onlyVisiblePart) {
		    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(600, 0, 0, 2))
			    .takeScreenshot(Driver);
		    img = myScreenshot.getImage();
		} else {
		    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(600)).takeScreenshot(Driver);
		    img = myScreenshot.getImage();
		}
		File origscr = new File(imageFilePath);
		ImageIO.write(img, "png", origscr);
	    } catch (Exception ex) {
		Reporter.log("Could not able to take screenshot");
	    }
	} else {
	    return;
	}
    }

    /* Overloaded method with timeout parmeter so that timeout time is waited before taking the screenshot
     * 
     */
    public void captureScreenshot(WebDriver Driver, String filename, String screenShotCompareFlag, Boolean onlyVisiblePart,
	    Integer timeout) {
	if (screenShotCompareFlag.equalsIgnoreCase("true")) {
	    try {
		Thread.sleep(timeout);
		BufferedImage img = null;
		String imageFilePath = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + filename + ".png";
		System.out.println(this.platformName);
		// Take screenshot and save to file
		if (onlyVisiblePart) {
		    File screenshot = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
		    img = ImageIO.read(screenshot);
		} else if (this.platformName.equalsIgnoreCase("Mac") && !onlyVisiblePart) {
		    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(600, 0, 0, 2))
			    .takeScreenshot(Driver);
		    img = myScreenshot.getImage();
		} else {
		    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1500)).takeScreenshot(Driver);
		    img = myScreenshot.getImage();
		}
		File origscr = new File(imageFilePath);
		ImageIO.write(img, "png", origscr);
	    } catch (Exception ex) {
		Reporter.log("Could not able to take screenshot");
	    }
	} else {
	    return;
	}
    }

    public void hideAndCaptureScreenshot(WebDriver Driver, String filename, String screenShotCompareFlag, Boolean onlyVisiblePart,
	    Integer timeout, String ignorePropertyName) {
	if (screenShotCompareFlag.equalsIgnoreCase("true")) {
	    try {
		Thread.sleep(timeout);
		hideElement(ignorePropertyName, Driver);
		BufferedImage img = null;
		String imageFilePath = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + filename + ".png";
		System.out.println(this.platformName);
		// Take screenshot and save to file
		if (onlyVisiblePart) {
		    File screenshot = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
		    img = ImageIO.read(screenshot);
		} else if (this.platformName.equalsIgnoreCase("Mac") && !onlyVisiblePart) {
		    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(600, 0, 0, 2))
			    .takeScreenshot(Driver);
		    img = myScreenshot.getImage();
		} else {
		    Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(600)).takeScreenshot(Driver);
		    img = myScreenshot.getImage();
		}
		File origscr = new File(imageFilePath);
		ImageIO.write(img, "png", origscr);
	    } catch (Exception ex) {
		Reporter.log("Could not able to take screenshot");
	    }
	} else {
	    return;
	}
    }

    /*
     * Method to capture screenshot using Selenium
     */
    public void captureScreenshot(WebDriver Driver, String filename, WebElement element) throws IOException {
	String imageFilePath = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + filename + ".png";
	// Screenshot myScreenshot = new AShot().takeScreenshot(Driver, element);
	Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportRetina(600, 0, 0, 2)).takeScreenshot(Driver,
		element);
	BufferedImage img = myScreenshot.getImage();
	File origscr = new File(imageFilePath);
	ImageIO.write(img, "png", origscr);
    }
    // public void captureScreenshot(WebDriver Driver, String filename, Boolean flag) throws IOException
    // {
    // String imageFilePath = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath")+filename+".png";
    // //Take screenshot and save to file
    // File screenshot=((TakesScreenshot)Driver).getScreenshotAs(OutputType.FILE);
    // BufferedImage img = ImageIO.read(screenshot);
    //
    // File origscr = new File(imageFilePath);
    // ImageIO.write(img, "png", origscr);
    // }

    /*
     * This method will hide an element from the page
     */
    public void hideElement(WebElement element, WebDriver Driver) throws Exception {
	try {
	    ((JavascriptExecutor) Driver).executeScript("arguments[0].style.visibility='hidden'", element);
	} catch (Exception e) {
	    throw new Exception("Could not able to hide element");
	}
    }

    private void loadIgnoreProperties() {
	String propertyFileName = PropsUtil.getEnvPropertyValue("ignoreElementPropertyListFilePath");
	try {
	    FileInputStream inputStream = new FileInputStream(propertyFileName);
	    properties.load(inputStream);
	} catch (IOException e) {
	    System.out.println(e.getLocalizedMessage());
	    e.printStackTrace();
	}
    }

    /*
     * This method will hide an element from the page
     */
    public void hideElement(String propertyName, WebDriver driver) throws Exception {
	loadIgnoreProperties();
	String elements = ImageCompareUtil.properties.getProperty(propertyName);
	String[] framesplit = propertyName.split("\\.");
	String pageName = framesplit[0];
	String frameName = null;
	if (framesplit.length > 1) {
	    frameName = framesplit[1];
	}
	String[] splitElementList = elements.split(",");
	if (frameName != null) {
	    PageParser.switchToFrame(driver, pageName, frameName);
	}
	for (int i = 0; i < splitElementList.length; i++) {
	    if (splitElementList[i].endsWith("*")) {
		String temp = splitElementList[i].substring(0, splitElementList[i].length() - 1);
		List<WebElement> webelements = SeleniumUtil.getWebElements(driver, temp, pageName, frameName);
		for (int j = 0; j < webelements.size(); j++) {
		    try {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='hidden'", webelements.get(j));
		    } catch (Exception e) {
			throw new Exception("Could not able to hide element");
		    }
		}
	    } else {
		WebElement singleelement = SeleniumUtil.getWebElement(driver, splitElementList[i], pageName, frameName);
		try {
		    ((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='hidden'", singleelement);
		} catch (Exception e) {
		    throw new Exception("Could not able to hide element");
		}
	    }
	}
    }

    public void customJS(WebDriver Driver) {
	((JavascriptExecutor) Driver).executeScript("alert($( this ).get( 0 ).nodeName)");
    }

    /*
     * This method accept buffered image and compare it.
     * This method is not used
     * 
     * 
     */
    public void compareImage(BufferedImage img1, BufferedImage img2) {
	int width1 = img1.getWidth(null);
	int width2 = img2.getWidth(null);
	int height1 = img1.getHeight(null);
	int height2 = img2.getHeight(null);
	if ((width1 != width2) || (height1 != height2)) {
	    System.err.println("Error: Images dimensions mismatch");
	    System.exit(1);
	}
	long diff = 0;
	for (int y = 0; y < height1; y++) {
	    for (int x = 0; x < width1; x++) {
		int rgb1 = img1.getRGB(x, y);
		int rgb2 = img2.getRGB(x, y);
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = (rgb1) & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = (rgb2) & 0xff;
		diff += Math.abs(r1 - r2);
		diff += Math.abs(g1 - g2);
		diff += Math.abs(b1 - b2);
	    }
	}
	double n = width1 * height1 * 3;
	double p = diff / n / 255.0;
	System.out.println("diff percent: " + (p * 100.0));
    }

    /* This method accepts screenshot name and search actual screenshot in the 
     * folder specified in  environment file.
     * 
     *  if Baseline image is not present in the expected folder then actual screenshot 
     *  is marked as baselined image.
     * 	
     *  Baseline and actual screenshot are present then it compare and generate a differnce image 
     *  and place that image in diffScreenshot folder specified in the environment file.
     *  
     *  If two screenshot are identical then method returns true else false
     * 
     */
    public Boolean compareImage(String imageName) throws IOException {
	String baselineImage = PropsUtil.getEnvPropertyValue("expectedScreenshotFilePath") + imageName + ".png";
	String actualImage = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + imageName + ".png";
	String resultantImage = PropsUtil.getEnvPropertyValue("diffScreenshotFilePath") + imageName + ".png";
	String myPath = PropsUtil.getEnvPropertyValue("imageMagikBinfolder");
	BufferedImage img1 = null;
	Boolean flag = true;
	if (new File(baselineImage).exists()) {
	    img1 = ImageIO.read(new File(baselineImage));
	} else {
	    if (new File(actualImage).renameTo(new File(baselineImage))) {
		Reporter.log("Baseline image was missing hence captured screenshot is marked baseline");
		return false;
	    }
	}
	BufferedImage img2 = ImageIO.read(new File(actualImage));
	int width1 = img1.getWidth(null);
	int width2 = img2.getWidth(null);
	int height1 = img1.getHeight(null);
	int height2 = img2.getHeight(null);
	if ((width1 != width2) || (height1 != height2)) {
	    Reporter.log("Error: Images dimensions mismatch");
	    flag = false;
	} else {
	    ProcessStarter.setGlobalSearchPath(myPath);
	    CompareCmd cmd = new CompareCmd();
	    IMOperation op = new IMOperation();
	    op.metric("ae");
	    op.addImage();
	    op.addImage();
	    op.addImage();
	    try {
		cmd.run(op, baselineImage, actualImage, resultantImage);
	    } catch (Exception ex) {
		flag = false;
		System.out.println(ex.getLocalizedMessage());
		// System.out.println("Hi");
	    }
	}
	String resultstring = "<a href=\"" + "../../" + actualImage + "\">" + "actualScreenShot</a>";
	String diff_resultstring = "<a href=\"" + "../../" + resultantImage + "\">" + "diffScreenShot</a>";
	System.out.println(flag);
	Reporter.log(resultstring);
	Reporter.log(diff_resultstring);
	return flag;
    }

    /* This method accepts actual screenshot path and chop the borders as specified in the parmeters
     * choptop: specified pixels are choped from top of the screenshot
     * chopbottom: specified pixels are choped from bottom of the screenshot
     * chopleft: specified pixels are choped from left of the screenshot
     * chopright: specified pixels are choped from right of the screenshot
     * and replace the screenshot with chopped one
     * 
     */
    public Boolean chopImage(String imageName, String choptop, String chopbottom, String chopleft, String chopright) {
	Boolean flag = true;
	String tempImage = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + "temp" + ".png";
	String myPath = PropsUtil.getEnvPropertyValue("imageMagikBinfolder");
	if (choptop.isEmpty() && chopleft.isEmpty() && chopright.isEmpty() && chopbottom.isEmpty()) {
	    Reporter.log("Image is not chopped");
	    return flag;
	}
	ProcessStarter.setGlobalSearchPath(myPath);
	ConvertCmd cmd = new ConvertCmd();
	IMOperation op = new IMOperation();
	op.addImage();
	if (!choptop.isEmpty()) {
	    op.chop(0, Integer.parseInt(choptop));
	}
	if (!chopleft.isEmpty()) {
	    op.chop(Integer.parseInt(chopleft), 0);
	}
	if (!chopright.isEmpty()) {
	    op.gravity("East");
	    op.chop(Integer.parseInt(chopright), 0);
	}
	if (!chopbottom.isEmpty()) {
	    op.gravity("South");
	    op.chop(0, Integer.parseInt(chopbottom));
	}
	op.addImage();
	try {
	    cmd.run(op, imageName, tempImage);
	    new File(tempImage).renameTo(new File(imageName));
	} //catch (IOException | InterruptedException | IM4JavaException e) {
	catch (Exception e) {
	    e.printStackTrace();
	    Reporter.log("Error: Could not able to chop screenshots");
	    flag = false;
	}
	return flag;
    }

    /* Overloaded method of compareImage with chop parameters to chop borders before comparing
     * 
     */
    public Boolean compareImage(String imageName, String choptop, String chopbottom, String chopleft, String chopright) throws IOException {
	String baselineImage = PropsUtil.getEnvPropertyValue("expectedScreenshotFilePath") + imageName + ".png";
	String actualImage = PropsUtil.getEnvPropertyValue("actualScreenshotFilePath") + imageName + ".png";
	String resultantImage = PropsUtil.getEnvPropertyValue("diffScreenshotFilePath") + imageName + ".png";
	String myPath = PropsUtil.getEnvPropertyValue("imageMagikBinfolder");
	BufferedImage img1 = null;
	Boolean flag = true;
	chopImage(actualImage, choptop, chopbottom, chopleft, chopright);
	if (new File(baselineImage).exists()) {
	    img1 = ImageIO.read(new File(baselineImage));
	} else {
	    if (new File(actualImage).renameTo(new File(baselineImage))) {
		EReporter.log("Baseline image was missing hence captured screenshot is marked baseline");
		return false;
	    }
	}
	BufferedImage img2 = ImageIO.read(new File(actualImage));
	int width1 = img1.getWidth(null);
	int width2 = img2.getWidth(null);
	int height1 = img1.getHeight(null);
	int height2 = img2.getHeight(null);
	if ((width1 != width2) || (height1 != height2)) {
	    EReporter.log("Error: Images dimensions mismatch");
	    flag = false;
	} else {
	    ProcessStarter.setGlobalSearchPath(myPath);
	    CompareCmd cmd = new CompareCmd();
	    IMOperation op = new IMOperation();
	    op.metric("ae");
	    op.addImage();
	    op.addImage();
	    op.addImage();
	    try {
		cmd.run(op, baselineImage, actualImage, resultantImage);
	    } catch (Exception ex) {
		flag = false;
		System.out.println(ex.getLocalizedMessage());
		// System.out.println("Hi");
	    }
	}
	String resultstring = "<a href=\"" + "../../" + actualImage + "\">" + "actualScreenShot</a>";
	String diff_resultstring = "<a href=\"" + "../../" + resultantImage + "\">" + "diffScreenShot</a>";
	System.out.println(flag);
	Reporter.log(resultstring);
	Reporter.log(diff_resultstring);
	return flag;
    }

    public Boolean compareImage(String baselineImage, String actualImage, String resultantImage) throws IOException {
	Boolean flag = true;
	BufferedImage img1 = ImageIO.read(new File(baselineImage));
	BufferedImage img2 = ImageIO.read(new File(baselineImage));
	int width1 = img1.getWidth(null);
	int width2 = img2.getWidth(null);
	int height1 = img1.getHeight(null);
	int height2 = img2.getHeight(null);
	if ((width1 != width2) || (height1 != height2)) {
	    System.err.println("Error: Images dimensions mismatch");
	    flag = false;
	}
	String myPath = "/usr/local/Cellar/imagemagick/6.8.9-7/bin";
	ProcessStarter.setGlobalSearchPath(myPath);
	CompareCmd cmd = new CompareCmd();
	IMOperation op = new IMOperation();
	op.metric("ae");
	op.addImage();
	op.addImage();
	op.addImage();
	try {
	    cmd.run(op, baselineImage, actualImage, resultantImage);
	} catch (Exception ex) {
	    flag = false;
	    System.out.println(ex.getLocalizedMessage());
	}
	return flag;
    }

    public Boolean takeScreenshotAndCompare(WebDriver driver, String filename, String screenShotCompareFlag) {
	Boolean success = true;
	try {
	    if (screenShotCompareFlag.equalsIgnoreCase("true")) {
		Thread.sleep(3000);
		captureScreenshot(driver, filename);
		success = compareImage(filename);
	    } else {
		EReporter.log("Screenshot comparision is not enabled for this suite");
	    }
	} catch (Exception ex) {
	    EReporter.log(ex.getMessage());
	}
	return success;
    }

    public static void main(String[] args) throws Exception {
	ImageCompareUtil imcmd = new ImageCompareUtil();
	imcmd.chopImage("createAccountsGroupPage", "", "20", "", "8");
    }
}
