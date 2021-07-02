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
import java.util.List;

import com.google.gson.Gson;
public class ParseToCreateView {
	
	public static void main(String[] args) {
		Gson gson = new Gson();    
		CreateView createView = new CreateView();
		List<View> viewsList = new ArrayList<View>();
		View view = new View();
		Budget budget = new Budget();
		BudgetAmount budgetAmount = new BudgetAmount();
		budget.setBudgetAmount(budgetAmount);
		view.setName("MyView1");
		view.setDescription("Creating My view");
		view.setBudget(budget);		
		viewsList.add(view);
		createView.setView(viewsList);
	    String json = gson.toJson(createView);
	    System.out.println(json);
	}
}
