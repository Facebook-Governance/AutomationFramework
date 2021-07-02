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
package com.yodlee.insights.subscriptions.pojo;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;

public class Subscription {

	private String insightName;
	private JSONObject entityParameter;
	public String getInsightName() {
		return insightName;
	}
	public void setInsightName(String insightName) {
		this.insightName = insightName;
	}
	public JSONObject  getEntityParameter() {
		return entityParameter;
	}
	public void setEntityParameter(JSONObject entityParameter) {
		this.entityParameter = entityParameter;
	}
	
	

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(entityParameter).toHashCode();
	}

	/**
	 * Override Equals.
	 *
	 * @param other the other
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Subscription) == false) {
			return false;
		}
		Subscription subscription = ((Subscription) other);
		return new EqualsBuilder().append(entityParameter, subscription.entityParameter).isEquals();
	}

}
