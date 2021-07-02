/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.screenShotCompareSuite;

import java.net.MalformedURLException;

import org.databene.benerator.anno.Source;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.ImageCompareUtil;

public class SampleTest extends TestBase {

    public WebDriver driver;
    private ImageCompareUtil imageprocess;

    @BeforeTest(alwaysRun = true)
    public void init() throws MalformedURLException {
	doInitialization("ScreenShot Comparison");
	imageprocess = new ImageCompareUtil();

	System.out.println(TestBase.screenShotEnabledFlag);
	if (TestBase.screenShotEnabledFlag.equalsIgnoreCase("false")) {
	    System.out.println("################ I am in Init method #############");
	    throw new SkipException("ScreenShot Feature is not enabled");
	}

    }

    @Test(enabled = true, dataProvider = "feeder", priority = 1)
    @Source("/screenShotCompare/InputData/screenShotList.csv")
    public void compareAccountsPage(String testDescription, String className, String ScreenShotName, String enable, String chopTop,
	    String chopBottom, String chopLeft, String chopRight) throws Exception {
	if (enable.equalsIgnoreCase("true")) {
	    Assert.assertTrue(imageprocess.compareImage(ScreenShotName, chopTop, chopBottom, chopLeft, chopRight));
	} else {
	    throw new SkipException("This test case is not enabled for this cobrand/browser");
	}

    }

}
