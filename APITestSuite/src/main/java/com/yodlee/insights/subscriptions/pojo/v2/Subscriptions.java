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

public class Subscriptions {

	private ArrayList<Subscription> userSubscription;
	private ArrayList<Subscription> customerSubscription;
	public ArrayList<Subscription> getUserSubscription() {
		return userSubscription;
	}
	public void setUserSubscription(ArrayList<Subscription> userSubscription) {
		this.userSubscription = userSubscription;
	}
	public ArrayList<Subscription> getCustomerSubscription() {
		return customerSubscription;
	}
	public void setCustomerSubscription(ArrayList<Subscription> customerSubscription) {
		this.customerSubscription = customerSubscription;
	}
	

}
