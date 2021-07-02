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

package com.yodlee.yodleeApi.schemaValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.everit.json.schema.FormatValidator;

/**
 * This class validates status of a Provider Accounts i.e. if it is IN_PROGRESS,
 * FAILED, SUCCESS etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumProviderAccountStatus implements FormatValidator {

	static List<String> providerAccStatus = new ArrayList<String>();

	static {
		providerAccStatus.add("IN_PROGRESS");
		providerAccStatus.add("FAILED");
		providerAccStatus.add("PARTIAL_SUCCESS");
		providerAccStatus.add("SUCCESS");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!providerAccStatus.contains(subject)) {
			return Optional.of(String.format("Invalid Provider Account Status " + subject, subject));
		} else {
			return Optional.empty();
		}
	}
}
