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

public class TestInfo {
	
	String testCaseId;
	String testDescription;
	String testStatus;
	String skippedSource;
	String response;
	String validations;
	String reasonForFailure;
	String testsPassed;
	String testsFailed;
	String passPercentage;
	String httpMethod = "GET";
	String testPriority;	
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}
	public String getSkippedSource() {
		return skippedSource;
	}
	public void setSkippedSource(String skippedSource) {
		this.skippedSource = skippedSource;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getReasonForFailure() {
		return reasonForFailure;
	}
	public void setReasonForFailure(String reasonForFailure) {
		this.reasonForFailure = reasonForFailure;
	}
	public String getValidations() {
		return validations;
	}
	public void setValidations(String validations) {
		this.validations = validations;
	}
	public String getTestsPassed() {
		return testsPassed;
	}
	public void setTestsPassed(String testsPassed) {
		this.testsPassed = testsPassed;
	}
	public String getTestsFailed() {
		return testsFailed;
	}
	public void setTestsFailed(String testsFailed) {
		this.testsFailed = testsFailed;
	}
	public String getPassPercentage() {
		int passPercentage =((Integer.parseInt(getTestsFailed())*100)/(Integer.parseInt(getTestsPassed())+Integer.parseInt(getTestsFailed())));		
		return String.valueOf(passPercentage);
	}
	public void setPassPercentage(String passPercentage) {
		this.passPercentage = passPercentage;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getTestPriority() {
		return testPriority;
	}
	public void setTestPriority(String testPriority) {
		this.testPriority = testPriority;
	}

}
