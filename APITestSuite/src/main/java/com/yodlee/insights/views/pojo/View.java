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
import java.util.List;

public class View {
	
	private String name;
	private String description;
	private Budget budget;
	private List<Rule> rule;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Budget getBudget() {
		return budget;
	}
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	public List<Rule> getRule() {
		return rule;
	}
	public void setRule(List<Rule> rule) {
		this.rule = rule;
	}
	
}
