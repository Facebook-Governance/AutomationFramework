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
 * This class validates type of MFA like if it is:-- "Token Id based multi
 * factor authentication" or "Master cookie identifies the request and skips
 * MFA" or "Question and answer type multi factor authentication." etc.
 * 
 * @author Rahul Kumar
 *
 */
public class MFAType implements FormatValidator {
	static List<String> mfaType = new ArrayList<String>();

	static {
		mfaType.add("Token Id based multi factor authentication");
		mfaType.add("Image based multifactor authentication");
		mfaType.add("Master cookie identifies the request and skips MFA");
		mfaType.add("Multiple levels of strong authentication.");
		mfaType.add("Question and answer type multi factor authentication.");

	}

	@Override
	public Optional<String> validate(final String subject) {

		if (!mfaType.contains(subject)) {
			return Optional.of(String.format("Invalid mfaType  " + subject, subject));
		} else {
			return Optional.empty();
		}

	}
}
