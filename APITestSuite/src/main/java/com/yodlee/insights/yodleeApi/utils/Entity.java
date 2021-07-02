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
package com.yodlee.insights.yodleeApi.utils;

import java.util.ArrayList;

public class Entity {

	private String entityType;
	private String frequency;
	private boolean isSubscribed;
	private String insightType;
	private String duration;
	private String id;
	private ArrayList<Threshold> thresholds;
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public boolean isSubscribed() {
		return isSubscribed;
	}
	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	public String getInsightType() {
		return insightType;
	}
	public void setInsightType(String insightType) {
		this.insightType = insightType;
	}
	public ArrayList<Threshold> getThresholds() {
		return thresholds;
	}
	public void setThresholds(ArrayList<Threshold> thresholds) {
		this.thresholds = thresholds;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
