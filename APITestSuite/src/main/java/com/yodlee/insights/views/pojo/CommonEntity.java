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
package com.yodlee.insights.views.pojo;
/**
 * Pojo class 
 * 
 * @author kemparaja
 *
 */
import java.util.ArrayList;
import java.util.HashMap;

import com.yodlee.insights.yodleeApi.utils.ExpectedResultValues;
import com.yodlee.yodleeApi.pojo.EnvSession;

public class CommonEntity {
	
	private HashMap<String,String> entityIdsMap=null;
	private HashMap<String,ArrayList> itemAccountsToTxnsMap=null;
	private String userJwtToken = null;
	private String viewRuleAccountNames = null;
	private String viewRuleTransactionDetails = null;
	private String viewRuleAmountRangeDetails = null;
	private String viewRuleCategoryIdDetails = null;
	private String verifyTriggerCheck = null;
	private String viewRuleMerchantNameDetails = null;
	private String viewRuleMerchantTypeDetails = null;
	private int thresholdLimit = 0;
	private EnvSession envSession = null;
	private ExpectedResultValues expectedResultValues = null;
	
	public HashMap<String, String> getEntityIdsMap() {
		return entityIdsMap;
	}
	public void setEntityIdsMap(HashMap<String, String> entityIdsMap) {
		this.entityIdsMap = entityIdsMap;
	}
	public HashMap<String, ArrayList> getItemAccountsToTxnsMap() {
		return itemAccountsToTxnsMap;
	}
	public void setItemAccountsToTxnsMap(HashMap<String, ArrayList> itemAccountsToTxnsMap) {
		this.itemAccountsToTxnsMap = itemAccountsToTxnsMap;
	}
	public String getUserJwtToken() {
		return userJwtToken;
	}
	public void setUserJwtToken(String userJwtToken) {
		this.userJwtToken = userJwtToken;
	}
	public String getViewRuleAccountNames() {
		return viewRuleAccountNames;
	}
	public void setViewRuleAccountNames(String viewRuleAccountNames) {
		this.viewRuleAccountNames = viewRuleAccountNames;
	}	
	public String getViewRuleTransactionDetails() {
		return viewRuleTransactionDetails;
	}
	public void setViewRuleTransactionDetails(String viewRuleTransactionDetails) {
		this.viewRuleTransactionDetails = viewRuleTransactionDetails;
	}
	public String getViewRuleAmountRangeDetails() {
		return viewRuleAmountRangeDetails;
	}
	public void setViewRuleAmountRangeDetails(String viewRuleAmountRangeDetails) {
		this.viewRuleAmountRangeDetails = viewRuleAmountRangeDetails;
	}
	public String getViewRuleCategoryIdDetails() {
		return viewRuleCategoryIdDetails;
	}
	public void setViewRuleCategoryIdDetails(String viewRuleCategoryIdDetails) {
		this.viewRuleCategoryIdDetails = viewRuleCategoryIdDetails;
	}
	public String getVerifyTriggerCheck() {
		return verifyTriggerCheck;
	}
	public void setVerifyTriggerCheck(String verifyTriggerCheck) {
		this.verifyTriggerCheck = verifyTriggerCheck;
	}
	public String getViewRuleMerchantNameDetails() {
		return viewRuleMerchantNameDetails;
	}
	public void setViewRuleMerchantNameDetails(String viewRuleMerchantNameDetails) {
		this.viewRuleMerchantNameDetails = viewRuleMerchantNameDetails;
	}
	public String getViewRuleMerchantTypeDetails() {
		return viewRuleMerchantTypeDetails;
	}
	public void setViewRuleMerchantTypeDetails(String viewRuleMerchantTypeDetails) {
		this.viewRuleMerchantTypeDetails = viewRuleMerchantTypeDetails;
	}
	public int getThresholdLimit() {
		return thresholdLimit;
	}
	public void setThresholdLimit(int thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}
	public EnvSession getEnvSession() {
		return envSession;
	}
	public void setEnvSession(EnvSession envSession) {
		this.envSession = envSession;
	}
	public ExpectedResultValues getExpectedResultValues() {
		return expectedResultValues;
	}
	public void setExpectedResultValues(ExpectedResultValues expectedResultValues) {
		this.expectedResultValues = expectedResultValues;
	}
	

}
