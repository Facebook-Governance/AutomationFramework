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
 * This class validates source of provider i.e. if it is SYSTEM or USER etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumProviderAccountAggSource implements FormatValidator {

	static List<String> providerAggSource = new ArrayList<String>();
	static {
		providerAggSource.add("SYSTEM");
		providerAggSource.add("USER");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!providerAggSource.contains(subject)) {
			return Optional.of(String.format("Invalid Transaction Status " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
