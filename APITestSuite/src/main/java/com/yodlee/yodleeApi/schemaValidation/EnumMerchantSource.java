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
 * This class validates source of the merchants i.e. whether it is FACTUAL or
 * Yodlee etc.
 * 
 * @author Rahul Kumar
 *
 */
public class EnumMerchantSource implements FormatValidator {

	static List<String> merchantSource = new ArrayList<String>();
	static {
		merchantSource.add("FACTUAL");
		merchantSource.add("YODLEE");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!merchantSource.contains(subject)) {
			return Optional.of(String.format("Invalid Merchant Source " + subject, subject));
		} else {
			return Optional.empty();
		}

	}

}
