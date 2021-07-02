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

import java.util.ArrayList;
import java.util.List;

public class ToMoveCommon {

	/**
	 * @param subrand
	 * @param cobrand
	 * @return
	 */
	public List<String> getExecutionMode(String subrand, String cobrand) {
		List<String> executionMode = new ArrayList<>();
		if ("TRUE".equalsIgnoreCase(subrand)) {
			executionMode.add("SUBRAND");
		}

		if ("TRUE".equalsIgnoreCase(cobrand)) {
			executionMode.add("COBRAND");
		}

		return executionMode;
	}

}
