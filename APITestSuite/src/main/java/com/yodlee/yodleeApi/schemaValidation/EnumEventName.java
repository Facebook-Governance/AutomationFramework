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
 * This class validates names of the Events like REFRESH and DATA_UPDATES
 * 
 * @author Rahul Kumar
 *
 */
public class EnumEventName implements FormatValidator {

	static List<String> eventName = new ArrayList<String>();

	static {
		eventName.add("REFRESH");
		eventName.add("DATA_UPDATES");

	}

	@Override
	public Optional<String> validate(final String subject) {
		if (!eventName.contains(subject)) {
			return Optional.of(String.format("Invalid EventName: " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
