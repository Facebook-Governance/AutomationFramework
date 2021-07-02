/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.helper;

import com.yodlee.yodleeApi.pojo.User;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		User u = User.builder().build();
		System.out.println(u.getUsernameKey());
		

	}

}
