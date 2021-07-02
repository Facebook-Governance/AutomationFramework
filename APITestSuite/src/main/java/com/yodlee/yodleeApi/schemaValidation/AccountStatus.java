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
 * This class validates account status like if the account
 * is active, blocked, closed etc.
 * 
 * @author Rahul Kumar
 *
 */
public class AccountStatus implements FormatValidator {
	static List<String> accountStatus = new ArrayList<String>();
	static {
		accountStatus.add("ACTIVE");
		accountStatus.add("INACTIVE");
		accountStatus.add("SUSPENDED");
		accountStatus.add("BLOCKED");
		accountStatus.add("DELETED");
		accountStatus.add("CLOSED");
		accountStatus.add("DORMANT");
	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!accountStatus.contains(subject)) {
			return Optional.of(String.format("Invalid account Status " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
