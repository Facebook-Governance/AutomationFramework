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
 * This class validates authentication type like OAUTH,CREDENTIALS and
 * MFA_CREDENTIALS
 * 
 * @author Rahul Kumar
 *
 */
public class AuthType implements FormatValidator {

	static List<String> authType = new ArrayList<String>();
	static {
		authType.add("OAUTH");
		authType.add("CREDENTIALS");
		authType.add("MFA_CREDENTIALS");
	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!authType.contains(subject)) {
			return Optional.of(String.format("Invalid AuthType  " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
