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
import java.util.HashSet;

public class EntityDetail {

	private boolean isSubscribed;
	private String frequency;
	private String duration;
	private String insightType;
	private String id;
	private HashSet<Threshold> threshold;
	public boolean isSubscribed() {
		return isSubscribed;
	}
	public void setSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getInsightType() {
		return insightType;
	}
	public void setInsightType(String insightType) {
		this.insightType = insightType;
	}
	public HashSet<Threshold> getThresholds() {
		return threshold;
	}
	public void setThresholds(HashSet<Threshold> threshold) {
		this.threshold = threshold;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


}
