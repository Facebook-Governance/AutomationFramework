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

import java.util.HashMap;

import com.google.gson.JsonArray;
import com.yodlee.insights.yodleeApi.utils.v2.Entities;

public class ExpectedResultValues {

	private String testCaseId;
	private String insightName;
	private String triggerType;
	private String entityParameterName;
	private String thresholdNameValueType;
	private int numberOfInsights;
	private int numberOfKeys;
	private boolean isUserPatched;
	private boolean isCobrandSubscribed;
	private HashMap<String,Boolean> cobrandSubscriptionMap;
	private int numberOfPatchedInisghts;
	private FailureReason failureReason;
	private JsonArray keysArray;
	private HashMap<String,String> entityIdsMap;
	private String validateEntityKeys;
	private HashMap<String,Entities> subscriptions;
	private boolean testNotificationStatus = true;

	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getInsightName() {
		return insightName;
	}
	public void setInsightName(String insightName) {
		this.insightName = insightName;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	public String getEntityParameterName() {
		return entityParameterName;
	}
	public void setEntityParameterName(String entityParameterName) {
		this.entityParameterName = entityParameterName;
	}
	public String getThresholdNameValueType() {
		return thresholdNameValueType;
	}
	public void setThresholdNameValueType(String thresholdNameValueType) {
		this.thresholdNameValueType = thresholdNameValueType;
	}
	public int getNumberOfInsights() {
		return numberOfInsights;
	}
	public void setNumberOfInsights(int numberOfInsights) {
		this.numberOfInsights = numberOfInsights;
	}
	public int getNumberOfKeys() {
		return numberOfKeys;
	}
	public void setNumberOfKeys(int numberOfKeys) {
		this.numberOfKeys = numberOfKeys;
	}
	public boolean isUserPatched() {
		return isUserPatched;
	}
	public void setUserPatched(boolean isUserPatched) {
		this.isUserPatched = isUserPatched;
	}
	
	public FailureReason getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(FailureReason failureReason) {
		this.failureReason = failureReason;
	}
	public JsonArray getKeysArray() {
		return keysArray;
	}
	public void setKeysArray(JsonArray keysArray) {
		this.keysArray = keysArray;
	}
	public HashMap<String, String> getEntityIdsMap() {
		return entityIdsMap;
	}
	public void setEntityIdsMap(HashMap<String, String> entityIdsMap) {
		this.entityIdsMap = entityIdsMap;
	}
	public String getValidateEntityKeys() {
		return validateEntityKeys;
	}
	public void setValidateEntityKeys(String validateEntityKeys) {
		this.validateEntityKeys = validateEntityKeys;
	}
	public HashMap<String, Entities> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(HashMap<String, Entities> subscriptions) {
		this.subscriptions = subscriptions;
	}
	public int getNumberOfPatchedInisghts() {
		return numberOfPatchedInisghts;
	}
	public void setNumberOfPatchedInisghts(int numberOfPatchedInisghts) {
		this.numberOfPatchedInisghts = numberOfPatchedInisghts;
	}
	public HashMap<String, Boolean> getCobrandSubscriptionMap() {
		return cobrandSubscriptionMap;
	}
	public void setCobrandSubscriptionMap(HashMap<String, Boolean> isCobrandSubscribedMap) {
		this.cobrandSubscriptionMap = isCobrandSubscribedMap;
	}
	public boolean getCobrandSubscribed() {
		return isCobrandSubscribed;
	}
	public void setCobrandSubscribed(boolean isCobrandSubscribed) {
		this.isCobrandSubscribed = isCobrandSubscribed;
	}
	public boolean getTestNotificationStatus() {
		return testNotificationStatus;
	}
	public void setTestNotificationStatus(boolean testNotificationStatus) {
		this.testNotificationStatus = testNotificationStatus;
	}
	


}
