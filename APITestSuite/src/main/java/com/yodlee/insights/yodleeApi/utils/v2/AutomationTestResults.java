/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.insights.yodleeApi.utils.v2;

import java.text.DecimalFormat;
import java.util.List;

public class AutomationTestResults {
	
	String insights;
	String buildNo;
	String userName;
	String codeCoveragePercent;
	String executionDate;
	List<TestInfo> allTestsResults;
	String storyId;
	String testsPassPercentage;
	
	public String getInsights() {
		return insights;
	}
	public void setInsights(String insights) {
		this.insights = insights;
	}
	public String getBuildNo() {
		return buildNo;
	}
	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCodeCoveragePercent() {
		return codeCoveragePercent;
	}
	public void setCodeCoveragePercent(String codeCoveragePercent) {
		this.codeCoveragePercent = codeCoveragePercent;
	}
	public String getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}
	public List<TestInfo> getTestInformation() {
		return allTestsResults;
	}
	public void setTestInformation(List<TestInfo> allTestsResults) {
		this.allTestsResults = allTestsResults;
	}
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	public String getTestsPassPercentage(TestInfo testInfo) {
		double passPercentage = (Double.parseDouble(testInfo.getTestsPassed())/(Double.parseDouble(testInfo.getTestsPassed())+Double.parseDouble(testInfo.getTestsFailed())))*100.0;
		DecimalFormat df = new DecimalFormat("#.##");
		double percentage = Double.parseDouble(df.format(passPercentage));
		return String.valueOf(percentage);
	}
	public void setTestsPassPercentage(String testsPassPercentage) {
		this.testsPassPercentage = testsPassPercentage;
	}
	
	

}

