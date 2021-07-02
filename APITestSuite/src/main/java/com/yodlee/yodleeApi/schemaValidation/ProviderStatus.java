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
 * This class validates status of Provider i.e. if it is Beta,Supported, Not
 * Supported.
 * 
 * @author Rahul Kumar
 *
 */
public class ProviderStatus implements FormatValidator {

	static List<String> providerStatus = new ArrayList<String>();
	static {
		providerStatus.add("Supported");
		providerStatus.add("Not Supported");
		providerStatus.add("Beta");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!providerStatus.contains(subject)) {
			return Optional.of(String.format("Invalid provider Status " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
