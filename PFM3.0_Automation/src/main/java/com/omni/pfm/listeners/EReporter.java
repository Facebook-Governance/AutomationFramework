/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.listeners;

import org.testng.Reporter;

import com.omni.pfm.testBase.TestBase;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author msuhaib
 *
 */
public class EReporter extends Reporter {
    public static void log(LogStatus status, String message) {
	Reporter.log(message);
	if (TestBase.tc != null) {
	    TestBase.tc.log(status, message);
	}
    }
}
