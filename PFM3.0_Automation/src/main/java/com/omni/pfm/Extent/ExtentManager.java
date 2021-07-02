/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.Extent;

import java.io.File;

import com.omni.pfm.config.Config;
import com.relevantcodes.extentreports.ExtentReports;

/**
 * @author msuhaib
 *
 */
public class ExtentManager {
    public static String ExtentFileName = null;

    public static ExtentReports Instance() {
	ExtentReports extent;
	// String Config.getInstance().getEnvironment()
	String fileName = Config.getInstance().getEnvironment() + "_ExtentReport";
	String ext = ".html";
	String path = "./target/html/" + fileName + ext;
	File f = new File(path);
	int count = 2;
	while (true) {
	    if (!f.exists()) {
		break;
	    }
	    path = "./target/html/" + fileName + "(" + count++ + ")" + ext;
	    f = new File(path);
	}
	extent = new ExtentReports(path, true);
	ExtentFileName = path;
	return extent;
    }

    public static String getExtentFileName() {
	return ExtentFileName;
    }

    public static void main(String[] args) {
	int i = 3;
	while (i > 0) {
	    ExtentManager.Instance();
	    i--;
	}
    }
    /* public static String CaptureScreen(WebDriver driver, String ImagesPath) {
    TakesScreenshot oScn = (TakesScreenshot) driver;
    File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
    File oDest = new File(ImagesPath + ".jpg");
    try {
        FileUtils.copyFile(oScnShot, oDest);
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
    return ImagesPath + ".jpg";
    }*/
}
