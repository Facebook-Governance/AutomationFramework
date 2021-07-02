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
public class Budget {

	private String budgetType="MONTHLY_RECURRING";
	private BudgetAmount budgetAmount;	
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	public BudgetAmount getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(BudgetAmount budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
}
