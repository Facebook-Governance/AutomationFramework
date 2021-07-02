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
package com.yodlee.insights.subscriptions.pojo.v2;

import java.util.ArrayList;

public class Subscription {

	private String insightName;
	private String insightTitle;
	private String insightType;
	private String triggerType;
	private String[] container;
	private String description;
	private String[] applicableEntity;
	private ArrayList<Configuration> customerConfiguration;
	private ArrayList<Configuration> userConfiguration;
	public String getInsightName() {
		return insightName;
	}
	public void setInsightName(String insightName) {
		this.insightName = insightName;
	}
	public String getInsightTitle() {
		return insightTitle;
	}
	public void setInsightTitle(String insightTitle) {
		this.insightTitle = insightTitle;
	}
	public String getInsightType() {
		return insightType;
	}
	public void setInsightType(String insightType) {
		this.insightType = insightType;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getApplicableEntity() {
		return applicableEntity;
	}
	public void setApplicableEntity(String[] applicableEntity) {
		this.applicableEntity = applicableEntity;
	}
	public ArrayList<Configuration> getCustomerConfiguration() {
		return customerConfiguration;
	}
	public void setCustomerConfiguration(ArrayList<Configuration> customerConfiguration) {
		this.customerConfiguration = customerConfiguration;
	}
	public ArrayList<Configuration> getUserConfiguration() {
		return userConfiguration;
	}
	public void setUserConfiguration(ArrayList<Configuration> userConfiguration) {
		this.userConfiguration = userConfiguration;
	}
	public String[] getContainer() {
		return container;
	}
	public void setContainer(String[] container) {
		this.container = container;
	}
	
	
	
	
}
